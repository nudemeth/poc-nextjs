package nudemeth.poc.ordering.infrastructure.eventbus

trait IntegrationEventHandlerOperations[TIntegrationEvent <: IntegrationEvent[TIntegrationEvent] with IntegrationEventProperties] {
  def handle(event: TIntegrationEvent)
}
