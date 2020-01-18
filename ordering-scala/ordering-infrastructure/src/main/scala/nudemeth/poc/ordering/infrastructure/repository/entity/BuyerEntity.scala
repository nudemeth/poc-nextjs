package nudemeth.poc.ordering.infrastructure.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

case class BuyerEntity(
  buyerId: UUID,
  name: String,
  paymentMethods: List[(String, String, String, String, OffsetDateTime, String)]) {

}
