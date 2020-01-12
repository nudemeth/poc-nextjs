package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }
import akka.pattern.pipe
import akka.util.Timeout
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, IdentifiedCommand, ShipOrderCommand }

import scala.concurrent.duration._
import nudemeth.poc.ordering.api.application.query.OrderQueryable
import nudemeth.poc.ordering.util.mediator.MediatorDuty
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService.UserIdentity

import scala.concurrent.ExecutionContext

object OrderingRegistryActor {
  final case class ActionPerformed(description: String)
  final case class GetOrders(userIdentity: UserIdentity)
  final case class GetCardTypes()
  final case class GetOrder(id: UUID)
  final case class CancelOrder(command: CancelOrderCommand, requestId: UUID)
  final case class ShipOrder(command: ShipOrderCommand, requestId: UUID)

  def props(repository: OrderQueryable, mediator: MediatorDuty): Props = Props(new OrderingRegistryActor(repository, mediator))
}

class OrderingRegistryActor(repository: OrderQueryable, mediator: MediatorDuty) extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.controller.OrderingRegistryActor._
  implicit val ec: ExecutionContext = context.dispatcher
  implicit val timeout: Timeout = 10.seconds

  def receive: Receive = {
    case GetOrders(userIdentity: UserIdentity) =>
      repository.getOrdersByUserIdAsync(userIdentity.id).pipeTo(sender())
    case GetOrder(id) =>
      repository.getOrderAsync(id).pipeTo(sender())
    case GetCardTypes() =>
      repository.getCardTypesAsync.pipeTo(sender())
    case CancelOrder(command, requestId) =>
      val requestCancelOrder = IdentifiedCommand[CancelOrderCommand, Boolean](command, requestId)
      mediator.send(requestCancelOrder).pipeTo(sender())
    case ShipOrder(command, requestId) =>
      val requestShipOrder = IdentifiedCommand[ShipOrderCommand, Boolean](command, requestId)
      mediator.send(requestShipOrder).pipeTo(sender())
  }
}