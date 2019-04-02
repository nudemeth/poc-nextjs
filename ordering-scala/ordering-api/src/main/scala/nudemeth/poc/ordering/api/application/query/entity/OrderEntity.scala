package nudemeth.poc.ordering.api.application.query.entity

import java.time.ZonedDateTime
import java.util.UUID

case class OrderEntity(
  orderId: UUID,
  orderDate: ZonedDateTime,
  description: String,
  addressCity: String,
  addressCountry: String,
  addressState: Option[String],
  addressStreet: String,
  addressZipCode: String,
  statusName: String,
  orderItems: Map[String, (Int, Double, String)]) {

}