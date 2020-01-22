package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

case class Buyer(buyerId: UUID, name: String, paymentMethods: Vector[PaymentMethod])
