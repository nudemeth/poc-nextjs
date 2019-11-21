package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

case class Order(
  orderId: UUID,
  userId: Option[String],
  userName: Option[String],
  address: Option[Address],
  cardTypeId: Option[Int],
  cardNumber: Option[String],
  cardSecurityNumber: Option[String],
  cardHolderName: Option[String],
  cardExpiration: Option[Instant],
  buyerId: Option[Int] = None,
  paymentMethodId: Option[Int] = None) {

}
