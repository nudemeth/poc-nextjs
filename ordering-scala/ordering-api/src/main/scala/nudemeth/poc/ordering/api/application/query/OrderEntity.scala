package nudemeth.poc.ordering.api.application.query

import java.time.ZonedDateTime
import java.util.UUID

case class OrderEntity(
  orderId: UUID,
  buyerId: UUID,
  orderDate: ZonedDateTime,
  description: String,
  addressCity: String,
  addressCountry: String,
  addressState: Option[String],
  addressStreet: String,
  addressZipCode: String,
  statusName: String,
  productName: String,
  units: Int,
  unitPrice: Double,
  pictureUrl: String) {

}
