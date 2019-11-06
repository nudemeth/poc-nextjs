package nudemeth.poc.ordering.api.application.query.viewmodel

import java.time.OffsetDateTime
import java.util.UUID

case class OrderSummary(
  orderNumber: UUID,
  date: OffsetDateTime,
  status: String,
  total: Double) {

}
