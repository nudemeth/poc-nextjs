package nudemeth.poc.ordering.infrastructure.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

case class OrderEntity(
  orderId: UUID,
  buyerId: UUID,
  orderDate: OffsetDateTime,
  description: String,
  addressCity: String,
  addressCountry: String,
  addressState: Option[String],
  addressStreet: String,
  addressZipCode: String,
  statusName: String,
  paymentMethodId: Option[UUID],
  orderItems: Map[UUID, (String, String, BigDecimal, BigDecimal, Int)]) {

}