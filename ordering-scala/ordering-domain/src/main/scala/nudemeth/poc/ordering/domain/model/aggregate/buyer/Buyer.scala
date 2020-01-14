package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

case class Buyer(id: UUID, name: String, vector: Vector[PaymentMethod])
