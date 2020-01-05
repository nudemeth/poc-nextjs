package nudemeth.poc.ordering.infrastructure.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

case class OrderByBuyerEntity(
  orderId: UUID,
  buyerId: UUID,
  orderDate: OffsetDateTime,
  statusName: String,
  total: Int) {

}