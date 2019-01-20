package nudemeth.poc.ordering.api.application.query.viewmodel

import com.outworkers.phantom.jdk8.ZonedDateTime

case class Order(
  orderNumber: Int,
  date: ZonedDateTime,
  status: String,
  description: String,
  street: String,
  city: String,
  zipCode: String,
  country: String,
  //orderItems: List[OrderItem],
  total: Double) {

}
