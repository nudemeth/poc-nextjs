package nudemeth.poc.ordering.domain.model.aggregate.order

import java.time.Instant
import java.util.UUID

case class Order(
  id: UUID,
  userId: String,
  userName: String,
  address: Option[Address],
  cardTypeId: Int,
  cardNumber: String,
  cardSecurityNumber: String,
  cardHolderName: String,
  cardExpiration: Option[Instant],
  buyerId: Option[Int] = None,
  paymentMethodId: Option[Int] = None) {

}
