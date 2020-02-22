package nudemeth.poc.ordering.domain.model.aggregate.order

import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.exception.OrderingDomainException
import nudemeth.poc.ordering.domain.model.{ AggregateRoot, Entity }
import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod
import nudemeth.poc.ordering.util.mediator.Notification

import scala.util.{ Failure, Success, Try }

case class OrderPayment(order: Order, paymentMethod: PaymentMethod, domainEvents: Vector[Notification]) extends Entity[OrderPayment](order.orderId, domainEvents) with AggregateRoot {
  override def addDomainEvent(domainEvent: Notification): OrderPayment = {
    this.copy(domainEvents = domainEvents :+ domainEvent)
  }

  override def removeDomainEvent(domainEvent: Notification): OrderPayment = {
    this.copy(domainEvents = domainEvents.filter(d => d != domainEvent))
  }

  override def clearDomainEvent(domainEvent: Notification): OrderPayment = {
    this.copy(domainEvents = Vector())
  }

  def setCancelledStatus(): Try[OrderPayment] = {
    if (order.orderStatus == "Paid" || order.orderStatus == "Shipped") {
      Failure(raiseStatusChangeException("Cancelled"))
    } else {
      val cancelledOrder = this.order.copy(orderStatus = "Cancelled", description = Some("The order was cancelled."))
      val cancelledOrderPayment = this.copy(order = cancelledOrder, domainEvents = domainEvents :+ OrderCancelledDomainEvent(cancelledOrder))
      Success(cancelledOrderPayment)
    }
  }

  def setShippedStatus(): Try[OrderPayment] = {
    if (order.orderStatus == "Paid") {
      Failure(raiseStatusChangeException("Shipped"))
    } else {
      val shippedOrder = this.order.copy(orderStatus = "Shipped", description = Some("The order was shipped."))
      val shippedOrderPayment = this.copy(order = shippedOrder, domainEvents = domainEvents :+ OrderCancelledDomainEvent(shippedOrder))
      Success(shippedOrderPayment)
    }
  }

  private def raiseStatusChangeException(nextStatus: String): OrderingDomainException = {
    OrderingDomainException(s"Is not possible to change the order status from ${order.orderStatus} to $nextStatus.")
  }
}
