package nudemeth.poc.ordering.infrastructure.eventbus

trait IntegrationEventHandlerOperations[TIntegrationEvent <: IntegrationEvent] {
  def handle(event: TIntegrationEvent)
}
