package nudemeth.poc.ordering.api.application.query

import java.time.ZonedDateTime
import java.util.UUID

case class OrderByUserEntity(
  orderId: UUID,
  buyerId: UUID,
  orderDate: ZonedDateTime,
  statusName: String,
  total: Int) {

}
