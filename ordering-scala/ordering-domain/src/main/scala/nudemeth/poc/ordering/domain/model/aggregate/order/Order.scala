package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.exception.OrderingDomainException
import nudemeth.poc.ordering.domain.model.Entity
import nudemeth.poc.ordering.util.mediator.Notification

import scala.util.{Failure, Success, Try}

case class Order(
                  orderId: UUID,
                  buyerId: UUID,
                  orderDate: Instant,
                  address: Address,
                  orderStatus: String,
                  orderItems: Vector[OrderItem],
                  description: Option[String]) extends Entity(orderId) {

  def setCancelledStatus(): Try[Order] = {
    if (orderStatus == "Paid" || orderStatus == "Shipped") {
      Failure(raiseStatusChangeException("Cancelled"))
    } else {
      val cancelledOrder = this.copy(orderStatus = "Cancelled", description = Some("The order was cancelled."))
      //val newDomainEvents = domainEvents :+ OrderCancelledDomainEvent(cancelledOrder)
      //AddDomainEvent
      Success(cancelledOrder)
    }
  }

  def setShippedStatus(): Try[Order] = {
    if (orderStatus != "Paid") {
      Failure(raiseStatusChangeException("Shipped"))
    } else {
      //AddDomainEvent
      Success(this.copy(orderStatus = "Shipped", description = Some("The order was shipped.")))
    }
  }

  private def raiseStatusChangeException(nextStatus: String): OrderingDomainException = {
    OrderingDomainException(s"Is not possible to change the order status from $orderStatus to $nextStatus.")
  }

}