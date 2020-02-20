package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

import nudemeth.poc.ordering.domain.model.{ AggregateRoot, Entity }

case class Buyer(buyerId: UUID, name: String, paymentMethods: Vector[PaymentMethod]) extends Entity(buyerId) with AggregateRoot
