package nudemeth.poc.ordering.api.application.query.viewmodel

import java.util.UUID

import com.outworkers.phantom.jdk8.ZonedDateTime

case class OrderSummary(
  orderNumber: UUID,
  date: ZonedDateTime,
  status: String,
  total: Double) {

}
