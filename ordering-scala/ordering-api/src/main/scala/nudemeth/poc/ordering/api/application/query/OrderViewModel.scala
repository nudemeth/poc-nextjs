package nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.jdk8.ZonedDateTime

case class OrderViewModel(
  orderNumber: Int,
  date: ZonedDateTime,
  status: String,
  description: String,
  street: String,
  city: String,
  zipCode: String,
  country: String,
  orderItems: List[OrderItemViewModel],
  total: Double) {

}
