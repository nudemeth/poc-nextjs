package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

import nudemeth.poc.ordering.domain.model.{ AggregateRoot, Entity }
import nudemeth.poc.ordering.util.mediator.Notification

case class Buyer(buyerId: UUID, name: String, paymentMethods: Vector[PaymentMethod], domainEvents: Vector[Notification]) extends Entity[Buyer](buyerId, domainEvents) with AggregateRoot {
  override def addDomainEvent(domainEvent: Notification): Buyer = {
    this.copy(domainEvents = domainEvents :+ domainEvent)
  }

  override def removeDomainEvent(domainEvent: Notification): Buyer = {
    this.copy(domainEvents = domainEvents.filter(d => d != domainEvent))
  }

  override def clearDomainEvent(domainEvent: Notification): Buyer = {
    this.copy(domainEvents = Vector())
  }
}
