package nudemeth.poc.ordering.api.application.query.viewmodel

import java.util.UUID

import com.outworkers.phantom.jdk8.ZonedDateTime

case class Order(
  orderNumber: UUID,
  date: ZonedDateTime,
  status: String,
  description: String,
  street: String,
  city: String,
  zipCode: String,
  country: String,
  orderItems: Vector[OrderItem],
  total: Double) {

}
