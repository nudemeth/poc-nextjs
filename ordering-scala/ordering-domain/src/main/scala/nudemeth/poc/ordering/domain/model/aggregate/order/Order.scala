package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

case class Order(
  orderId: UUID,
  buyerId: UUID,
  orderDate: Instant,
  address: Address,
  orderStatus: String,
  orderItems: Vector[OrderItem],
  description: Option[String]) {

}
