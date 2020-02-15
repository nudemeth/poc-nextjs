package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

import nudemeth.poc.ordering.domain.model.AggregateRoot

case class Buyer(buyerId: UUID, name: String, paymentMethods: Vector[PaymentMethod]) extends AggregateRoot
