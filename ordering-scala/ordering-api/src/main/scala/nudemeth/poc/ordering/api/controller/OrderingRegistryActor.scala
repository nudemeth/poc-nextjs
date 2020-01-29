package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, IdentifiedCommand, ShipOrderCommand }
import nudemeth.poc.ordering.api.application.query.OrderQueryable
import nudemeth.poc.ordering.api.application.query.viewmodel.{ CardType, Order, OrderSummary }
import nudemeth.poc.ordering.util.mediator.MediatorDuty
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService.UserIdentity

import scala.concurrent.ExecutionContext
import scala.util.{ Try, Failure, Success }

object OrderingRegistryActor {
  sealed trait Command
  final case class GetOrders(userIdentity: UserIdentity, replyTo: ActorRef[Try[Vector[OrderSummary]]]) extends Command
  final case class GetCardTypes(replyTo: ActorRef[Try[Vector[CardType]]]) extends Command
  final case class GetOrder(id: UUID, replyTo: ActorRef[Try[Option[Order]]]) extends Command
  final case class CancelOrder(command: CancelOrderCommand, requestId: UUID, replyTo: ActorRef[Try[Boolean]]) extends Command
  final case class ShipOrder(command: ShipOrderCommand, requestId: UUID, replyTo: ActorRef[Try[Boolean]]) extends Command

  private sealed trait Result extends Command
  private final case class GetOrdersResult(result: Try[Vector[OrderSummary]], replyTo: ActorRef[Try[Vector[OrderSummary]]]) extends Result
  private final case class GetOrderResult(result: Try[Option[Order]], replyTo: ActorRef[Try[Option[Order]]]) extends Result
  private final case class GetCardTypesResult(result: Try[Vector[CardType]], replyTo: ActorRef[Try[Vector[CardType]]]) extends Result
  private final case class CancelOrderResult(result: Try[Boolean], replyTo: ActorRef[Try[Boolean]]) extends Result
  private final case class ShipOrderResult(result: Try[Boolean], replyTo: ActorRef[Try[Boolean]]) extends Result

  def apply(repository: OrderQueryable, mediator: MediatorDuty): Behavior[Command] = Behaviors.receive { (ctx, message) =>
    implicit val executionContext: ExecutionContext = ctx.executionContext
    message match {
      case GetOrders(userIdentity, replyTo) =>
        ctx.pipeToSelf(repository.getOrdersByUserIdAsync(userIdentity.id)) {
          case Success(value) => GetOrdersResult(Success(value), replyTo)
          case Failure(ex) => GetOrdersResult(Failure(ex), replyTo)
        }
        Behaviors.same
      case GetOrder(id, replyTo) =>
        ctx.pipeToSelf(repository.getOrderAsync(id)) {
          case Success(value) => GetOrderResult(Success(value), replyTo)
          case Failure(ex) => GetOrderResult(Failure(ex), replyTo)
        }
        Behaviors.same
      case GetCardTypes(replyTo) =>
        ctx.pipeToSelf(repository.getCardTypesAsync) {
          case Success(value) => GetCardTypesResult(Success(value), replyTo)
          case Failure(ex) => GetCardTypesResult(Failure(ex), replyTo)
        }
        Behaviors.same
      case CancelOrder(command, requestId, replyTo) =>
        val requestCancelOrder = IdentifiedCommand[CancelOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestCancelOrder)) {
          case Success(value) => CancelOrderResult(Success(value), replyTo)
          case Failure(ex) => CancelOrderResult(Failure(ex), replyTo)
        }
        Behaviors.same
      case ShipOrder(command, requestId, replyTo) =>
        val requestShipOrder = IdentifiedCommand[ShipOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestShipOrder)) {
          case Success(value) => ShipOrderResult(Success(value), replyTo)
          case Failure(ex) => ShipOrderResult(Failure(ex), replyTo)
        }
        Behaviors.same

      case GetOrdersResult(result: Try[Vector[OrderSummary]], replyTo: ActorRef[Try[Vector[OrderSummary]]]) =>
        replyTo ! result
        Behaviors.same
      case GetOrderResult(result: Try[Option[Order]], replyTo: ActorRef[Try[Option[Order]]]) =>
        replyTo ! result
        Behaviors.same
      case GetCardTypesResult(result: Try[Vector[CardType]], replyTo: ActorRef[Try[Vector[CardType]]]) =>
        replyTo ! result
        Behaviors.same
      case CancelOrderResult(result: Try[Boolean], replyTo: ActorRef[Try[Boolean]]) =>
        replyTo ! result
        Behaviors.same
      case ShipOrderResult(result: Try[Boolean], replyTo: ActorRef[Try[Boolean]]) =>
        replyTo ! result
        Behaviors.same
    }
  }
}