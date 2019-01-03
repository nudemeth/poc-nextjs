package nudemeth.poc.ordering.api.application.query

import java.time.ZonedDateTime
import java.util.UUID

case class OrderEntity(
  id: UUID,
  userId: String,
  userName: String,
  street: String,
  city: String,
  state: Option[String],
  country: String,
  zipCode: String,
  cardTypeId: Int,
  cardNumber: String,
  cardSecurityNumber: String,
  cardHolderName: String,
  cardExpiration: Option[ZonedDateTime],
  buyerId: Option[Int] = None,
  paymentMethodId: Option[Int] = None) {

}
