package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}

import scala.concurrent.{ExecutionContext, Future}

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

  var orders: List[Order] = List[Order](Order(UUID.randomUUID(), "a"), Order(UUID.randomUUID(), "b"))

  def receive: Receive = {
    case GetOrders =>
      sender() ! getOrders
    case GetOrder(id) =>
      sender() ! getOrder(id)
    case CreateOrder(order) =>
      createOrder(order)
      sender() ! ActionPerformed(s"Order ${order.name} created.")
    case DeleteOrder(id) =>
      deleteOrder(id)
      sender() ! ActionPerformed(s"Order id $id deleted.")
  }

  private def getOrders: Future[List[Order]] = Future {
    orders
  }(ExecutionContext.global)

  private def getOrder(id: UUID): Option[Order] = {
    orders.find(_.id == id)
  }

  private def createOrder(order: Order): Unit = {
    orders = orders :+ order
  }

  private def deleteOrder(id: UUID): Unit = {
    orders = orders.filterNot(_.id == id)
  }
}