package nudemeth.poc.ordering.infrastructure.eventbus

trait EventBusOperations {
  def publish(event: IntegrationEvent)
  def subscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()
  def unsubscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()
}
