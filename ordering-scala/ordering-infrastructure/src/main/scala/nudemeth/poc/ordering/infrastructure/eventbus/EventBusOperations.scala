package nudemeth.poc.ordering.infrastructure.eventbus

trait EventBusOperations {
  def publish[T <: IntegrationEvent[T] with IntegrationEventProperties](event: IntegrationEvent[T])
  def subscribe[T <: IntegrationEvent[T] with IntegrationEventProperties, TH <: IntegrationEventHandlerOperations[T]]()
  def unsubscribe[T <: IntegrationEvent[T] with IntegrationEventProperties, TH <: IntegrationEventHandlerOperations[T]]()
}
