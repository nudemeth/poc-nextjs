package nudemeth.poc.ordering.api.application.integrationevent.event

import java.time.Instant
import java.util.UUID

import nudemeth.poc.ordering.infrastructure.eventbus.IntegrationEvent

case class OrderStatusChangedToCancelledIntegrationEvent(id: UUID, creationDate: Instant, orderId: UUID, orderStatus: String, buyerName: String) extends IntegrationEvent(id, creationDate) {
  def this(orderId: UUID, orderStatus: String, buyerName: String) = {
    this(UUID.randomUUID(), Instant.now(), orderId, orderStatus, buyerName)
  }
}
