package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

import nudemeth.poc.ordering.domain.exception.OrderingDomainException
import nudemeth.poc.ordering.domain.model.Entity

case class Order(
  orderId: UUID,
  buyerId: UUID,
  orderDate: Instant,
  address: Address,
  orderStatus: String,
  orderItems: Vector[OrderItem],
  description: Option[String]) extends Entity(orderId) {

  def setCancelledStatus(): Order = {
    if (orderStatus == "Paid" || orderStatus == "Shipped") {
      raiseStatusChangeException("Cancelled")
    }
    //Entity.addDomainEvent()
    this.copy(orderStatus = "Cancelled", description = Some("The order was cancelled."))
  }

  def setShippedStatus(): Order = {
    if (orderStatus != "Paid") {
      raiseStatusChangeException("Shipped")
    }
    this.copy(orderStatus = "Shipped", description = Some("The order was shipped."))
    //AddDomainEvent
  }

  private def raiseStatusChangeException(nextStatus: String): Unit = {
    throw OrderingDomainException(s"Is not possible to change the order status from $orderStatus to $nextStatus.")
  }

}
