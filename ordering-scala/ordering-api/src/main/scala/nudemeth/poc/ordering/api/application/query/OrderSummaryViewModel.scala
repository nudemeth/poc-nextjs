package nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.jdk8.ZonedDateTime

case class OrderSummaryViewModel(orderNumber: Int,
                                 date: ZonedDateTime,
                                 status: String,
                                 total: Double) {

}
