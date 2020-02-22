package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.exception.OrderingDomainException
import nudemeth.poc.ordering.domain.model.Entity
import nudemeth.poc.ordering.util.mediator.Notification

import scala.util.{ Failure, Success, Try }

case class Order(
  orderId: UUID,
  buyerId: UUID,
  orderDate: Instant,
  address: Address,
  orderStatus: String,
  orderItems: Vector[OrderItem],
  description: Option[String]) {

}

