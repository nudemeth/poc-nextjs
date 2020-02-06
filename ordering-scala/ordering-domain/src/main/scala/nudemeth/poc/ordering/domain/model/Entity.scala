package nudemeth.poc.ordering.domain.model

import java.util.UUID

import nudemeth.poc.ordering.util.mediator.Notification

abstract class Entity(id: UUID) {

}

object Entity {
  def addDomainEvent(eventItem: Notification, domainEvents: Vector[Notification]): Vector[Notification] = {
    domainEvents :+ eventItem
  }
}
