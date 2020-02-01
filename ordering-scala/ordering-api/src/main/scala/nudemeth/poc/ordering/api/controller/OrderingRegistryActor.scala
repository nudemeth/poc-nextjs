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

  private def mapToResult[A, T <: Result](resultProcess: (Try[A], ActorRef[Try[A]]) => T, replyTo: ActorRef[Try[A]]): Try[A] => T = {
    case Success(value) => resultProcess(Success(value), replyTo)
    case Failure(ex) => resultProcess(Failure(ex), replyTo)
  }

  def apply(orderingQuery: OrderQueryable, mediator: MediatorDuty): Behavior[Command] = Behaviors.receive { (ctx, message) =>
    implicit val executionContext: ExecutionContext = ctx.executionContext
    message match {
      case GetOrders(userIdentity, replyTo) =>
        ctx.pipeToSelf(orderingQuery.getOrdersByUserIdAsync(userIdentity.id))(mapToResult(GetOrdersResult, replyTo))
        Behaviors.same
      case GetOrder(id, replyTo) =>
        ctx.pipeToSelf(orderingQuery.getOrderAsync(id))(mapToResult(GetOrderResult, replyTo))
        Behaviors.same
      case GetCardTypes(replyTo) =>
        ctx.pipeToSelf(orderingQuery.getCardTypesAsync)(mapToResult(GetCardTypesResult, replyTo))
        Behaviors.same
      case CancelOrder(command, requestId, replyTo) =>
        val requestCancelOrder = IdentifiedCommand[CancelOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestCancelOrder))(mapToResult(CancelOrderResult, replyTo))
        Behaviors.same
      case ShipOrder(command, requestId, replyTo) =>
        val requestShipOrder = IdentifiedCommand[ShipOrderCommand, Boolean](command, requestId)
        ctx.pipeToSelf(mediator.send(requestShipOrder))(mapToResult(ShipOrderResult, replyTo))
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