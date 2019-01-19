package nudemeth.poc.ordering.api.application.query.viewmodel

import com.outworkers.phantom.jdk8.ZonedDateTime

case class OrderSummary(
  orderNumber: Int,
  date: ZonedDateTime,
  status: String,
  total: Double) {

}
