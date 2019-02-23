package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }
import akka.pattern.pipe
import nudemeth.poc.ordering.api.application.query.OrderQueryable
import nudemeth.poc.ordering.api.application.query.viewmodel.Order

import scala.concurrent.ExecutionContext

object OrderingRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetOrders
  final case class CreateOrder(order: Order)
  final case class GetOrder(id: UUID)
  final case class DeleteOrder(id: UUID)

  def props(repository: OrderQueryable): Props = Props(new OrderingRegistryActor(repository))
}

class OrderingRegistryActor(repository: OrderQueryable) extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.controller.OrderingRegistryActor._
  implicit val ec: ExecutionContext = context.dispatcher

  def receive: Receive = {
    case GetOrders =>
      val userName = UUID.fromString("6bc6cfae-b04e-4b53-ba23-1a1b7260b121")
      repository.getOrdersByUserNameAsync(userName).pipeTo(sender())
    case GetOrder(id) =>
      repository.getOrderAsync(id).pipeTo(sender())
    case CreateOrder(order) =>
      sender() ! ""
    case DeleteOrder(id) =>
      val userName = UUID.fromString("6bc6cfae-b04e-4b53-ba23-1a1b7260b121")
      repository.deleteOrderAsync(id, userName).pipeTo(sender())
  }
}