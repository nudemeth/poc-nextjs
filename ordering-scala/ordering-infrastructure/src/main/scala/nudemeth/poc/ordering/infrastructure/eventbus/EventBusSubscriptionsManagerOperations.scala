package nudemeth.poc.ordering.infrastructure.eventbus

trait EventBusSubscriptionsManagerOperations {
  def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()
  def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()
}
