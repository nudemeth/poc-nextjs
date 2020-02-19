package nudemeth.poc.ordering.api

import akka.actor.typed.scaladsl.adapter._
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ ActorSystem, Behavior, PostStop }

import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.Materializer
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, CancelOrderCommandHandler, IdentifiedCommand, IdentifiedCommandHandler, ShipOrderCommand, ShipOrderCommandHandler }
import nudemeth.poc.ordering.api.application.domaineventhandler.OrderCancelledDomainEventHandler
import nudemeth.poc.ordering.api.application.integrationevent.{ OrderingIntegrationEventService, OrderingIntegrationEventServiceOperations }
import nudemeth.poc.ordering.api.application.query.{ OrderQuery, OrderQueryable }
import nudemeth.poc.ordering.api.controller.{ OrderingRegistryActor, OrderingRoutes }
import nudemeth.poc.ordering.util.mediator.{ Mediator, Notification, NotificationHandler, Request, RequestHandler }
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService
import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.model.aggregate.buyer.BuyerRepositoryOperations
import nudemeth.poc.ordering.infrastructure.{ Connector, OrderingContext }
import nudemeth.poc.ordering.infrastructure.eventbus.rabbitmq.EventBusRabbitMq
import nudemeth.poc.ordering.infrastructure.repository.{ BuyerRepository, OrderRepository }

//#main-class
object Server {
  sealed trait Command
  private final case class StartFailed(cause: Throwable) extends Command
  private final case class Started(binding: ServerBinding) extends Command
  private final case object Stop extends Command

  def apply(host: String, port: Int): Behavior[Command] = Behaviors.setup { ctx =>
    implicit val system: ActorSystem[_] = ctx.system
    implicit val untypedSystem: akka.actor.ActorSystem = ctx.system.toClassic
    implicit val materializer: Materializer = Materializer(ctx.system)
    implicit val executionContext: ExecutionContext = ctx.system.executionContext

    val orderingQuery: OrderQueryable = new OrderQuery()
    val orderingIntegrationEventService = OrderingIntegrationEventService(EventBusRabbitMq())

    val orderingContext: OrderingContext = OrderingContext(Connector.connector, mediator)
    lazy val orderRepo: OrderRepository = OrderRepository(orderingContext)
    lazy val buyerRepo: BuyerRepositoryOperations = BuyerRepository(orderingContext)
    lazy val handlers: Map[Class[_ <: Request[Any]], _ <: RequestHandler[_ <: Request[Any], Any]] = Map(
      classOf[CancelOrderCommand] -> CancelOrderCommandHandler(orderRepo),
      classOf[ShipOrderCommand] -> ShipOrderCommandHandler(orderRepo),
      classOf[IdentifiedCommand[CancelOrderCommand, Boolean]] -> IdentifiedCommandHandler[CancelOrderCommand, Boolean](),
      classOf[IdentifiedCommand[ShipOrderCommand, Boolean]] -> IdentifiedCommandHandler[ShipOrderCommand, Boolean]())
    lazy val notificationHandlers: Map[Class[_ <: Notification], _ <: NotificationHandler[_ <: Notification]] = Map(
      classOf[OrderCancelledDomainEvent] -> OrderCancelledDomainEventHandler(orderRepo, buyerRepo, orderingIntegrationEventService))
    lazy val mediator: Mediator = new Mediator(handlers, notificationHandlers)

    val identityRegistryActor = ctx.spawn(IdentityService(), "identity-actor")
    val orderingRegistryActor = ctx.spawn(OrderingRegistryActor(orderingQuery, mediator), "ordering-actor")
    val routes = new OrderingRoutes(orderingRegistryActor, identityRegistryActor)
    val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes.orderingRoutes, host, port)

    ctx.pipeToSelf(serverBinding) {
      case Success(binding) => Started(binding)
      case Failure(ex) => StartFailed(ex)
    }

    def running(binding: ServerBinding): Behavior[Command] = {
      Behaviors.receiveMessagePartial[Command] {
        case Stop =>
          ctx.log.info(s"Stopping server http://${binding.localAddress.getHostString}:${binding.localAddress.getPort}/")
          Behaviors.stopped
      }.receiveSignal {
        case (_, PostStop) =>
          binding.unbind()
          Behaviors.same
      }
    }

    def starting(wasStopped: Boolean): Behaviors.Receive[Command] = {
      Behaviors.receiveMessage[Command] {
        case StartFailed(cause) => throw new RuntimeException("Server failed to start", cause)
        case Started(binding) =>
          ctx.log.info(s"Server online at http://${binding.localAddress.getHostString}:${binding.localAddress.getPort}/")
          running(binding)
        case Stop => starting(true)
      }
    }

    starting(false)
  }

  def main(args: Array[String]): Unit = {
    val system: ActorSystem[Command] = ActorSystem(Server("localhost", 8080), "ordering-system")
    Await.result(system.whenTerminated, Duration.Inf)
  }

}
