package nudemeth.poc.ordering.infrastructure.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

case class OrderByIdEntity(
  orderId: UUID,
  buyerId: UUID,
  orderDate: OffsetDateTime,
  description: Option[String],
  addressCity: String,
  addressCountry: String,
  addressState: Option[String],
  addressStreet: String,
  addressZipCode: String,
  statusName: String,
  paymentMethodAlias: String,
  paymentMethodCardNumber: String,
  paymentMethodCardSecurityNumber: String,
  paymentMethodCardHolderName: String,
  paymentMethodCardExpiration: OffsetDateTime,
  paymentMethodCardType: String,
  orderItems: Map[UUID, (String, String, BigDecimal, BigDecimal, Int)]) {

}
