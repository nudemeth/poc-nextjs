package nudemeth.poc.ordering.domain.model

import java.util.UUID

import nudemeth.poc.ordering.util.mediator.Notification

abstract class Entity[T](id: UUID, domainEvents: Vector[Notification]) {
  def addDomainEvent(domainEvent: Notification): T
  def removeDomainEvent(domainEvent: Notification): T
  def clearDomainEvent(domainEvent: Notification): T
}