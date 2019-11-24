package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

case class Order(
  orderId: UUID,
  buyerId: Option[UUID],
  orderDate: Instant,
  address: Option[Address],
  orderStatus: String,
  orderItems: Vector[OrderItem],
  paymentMethodId: Option[UUID],
  description: Option[String]) {

}
