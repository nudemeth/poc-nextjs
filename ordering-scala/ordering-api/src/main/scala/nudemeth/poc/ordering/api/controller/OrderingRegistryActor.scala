package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }

final case class Order(id: UUID, name: String)

object OrderingRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetOrders
  final case class CreateOrder(order: Order)
  final case class GetOrder(id: UUID)
  final case class DeleteOrder(id: UUID)

  def props: Props = Props[OrderingRegistryActor]
}

class OrderingRegistryActor extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.controller.OrderingRegistryActor._

  var orders = List.empty[Order]

  def receive: Receive = {
    case GetOrders =>
      sender() ! orders
    case GetOrder(id) =>
      sender() ! orders.find(_.id == id)
    case CreateOrder(order) =>
      orders = orders :+ order
      sender() ! ActionPerformed(s"Order ${order.name} created.")
    case DeleteOrder(id) =>
      orders = orders.filterNot(_.id == id)
      sender() ! ActionPerformed(s"Order id $id deleted.")
  }
}