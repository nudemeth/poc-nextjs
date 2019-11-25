package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.time.Instant

import nudemeth.poc.ordering.domain.model.aggregate.buyer.CardType.CardType

case class PaymentMethod(
  alias: String,
  cardNumber: String,
  cardSecurityNumber: String,
  cardHolderName: String,
  cardExpiration: Instant,
  cardType: CardType) {

}
