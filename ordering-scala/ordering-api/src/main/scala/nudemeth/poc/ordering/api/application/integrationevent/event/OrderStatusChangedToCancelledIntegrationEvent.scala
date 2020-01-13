package nudemeth.poc.ordering.api.application.integrationevent.event

import java.util.UUID

import nudemeth.poc.ordering.infrastructure.eventbus.IntegrationEvent

case class OrderStatusChangedToCancelledIntegrationEvent(orderId: UUID, orderStatus: String, buyerName: String) extends IntegrationEvent
