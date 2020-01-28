package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

import scala.reflect.runtime.universe._
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, IdentifiedCommand, ShipOrderCommand }
import nudemeth.poc.ordering.api.application.query.OrderQueryable
import nudemeth.poc.ordering.api.application.query.viewmodel.{ CardType, Order, OrderSummary }
import nudemeth.poc.ordering.util.mediator.MediatorDuty
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService.UserIdentity

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

object OrderingRegistryActor {
  sealed trait Command
  final case class GetOrders(userIdentity: UserIdentity, replyTo: ActorRef[Vector[OrderSummary]]) extends Command
  final case class GetCardTypes(replyTo: ActorRef[Vector[CardType]]) extends Command
  final case class GetOrder(id: UUID, replyTo: ActorRef[Option[Order]]) extends Command
  final case class CancelOrder(command: CancelOrderCommand, requestId: UUID, replyTo: ActorRef[Boolean]) extends Command
  final case class ShipOrder(command: ShipOrderCommand, requestId: UUID, replyTo: ActorRef[Boolean]) extends Command

  sealed trait Result extends Command
  private final case class Reply[T: TypeTag](result: T, replyTo: ActorRef[T]) extends Result
  private final case class GetOrdersSuccess(result: Vector[OrderSummary], replyTo: ActorRef[Vector[OrderSummary]]) extends Result
  private final case class GetOrdersFailure(result: Vector[OrderSummary], replyTo: ActorRef[Vector[OrderSummary]]) extends Result

  def apply(repository: OrderQueryable, mediator: MediatorDuty): Behavior[Command] = Behaviors.receive { (ctx, message) =>
    implicit val executionContext: ExecutionContext = ctx.executionContext
    message match {
      case GetOrders(userIdentity, replyTo) =>
        ctx.pipeToSelf(repository.getOrdersByUserIdAsync(userIdentity.id)) {
          case Success(value) => GetOrdersSuccess(value, replyTo)
          case Failure(ex) => Reply(ex, replyTo)
        }
        Behaviors.same
      case GetOrder(id, replyTo) =>
        ctx.pipeToSelf(repository.getOrderAsync(id)) {
          case Success(value) => Reply(value, replyTo)
          case Failure(ex) => Reply(ex, replyTo)
        }
        Behaviors.same
      case GetCardTypes(replyTo) =>
        ctx.pipeToSelf(repository.getCardTypesAsync) {
          case Success(value) => Reply(value, replyTo)
          case Failure(ex) => Reply(ex, replyTo)
        }
        Behaviors.same
      case CancelOrder(command, requestId, replyTo) =>
        val requestCancelOrder = IdentifiedCommand[CancelOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestCancelOrder)) {
          case Success(value) => Reply(value, replyTo)
          case Failure(ex) => Reply(ex, replyTo)
        }
        Behaviors.same
      case ShipOrder(command, requestId, replyTo) =>
        val requestShipOrder = IdentifiedCommand[ShipOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestShipOrder)) {
          case Success(value) => Reply(value, replyTo)
          case Failure(ex) => Reply(ex, replyTo)
        }
        Behaviors.same

      case GetOrdersSuccess(result: Vector[OrderSummary], replyTo: ActorRef[Vector[OrderSummary]]) =>
        replyTo ! result
        Behaviors.same
      case Reply(result: Option[Order], replyTo: ActorRef[Option[Order]]) =>
        replyTo ! result
        Behaviors.same
      case Reply(result: Vector[CardType], replyTo: ActorRef[Vector[CardType]]) =>
        replyTo ! result
        Behaviors.same
      case Reply(result: Boolean, replyTo: ActorRef[Boolean]) =>
        replyTo ! result
        Behaviors.same
    }
  }
}