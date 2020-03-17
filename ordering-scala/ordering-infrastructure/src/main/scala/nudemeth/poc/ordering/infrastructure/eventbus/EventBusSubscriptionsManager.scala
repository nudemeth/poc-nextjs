package nudemeth.poc.ordering.infrastructure.eventbus

trait EventBusSubscriptionsManager {
  def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Map[String, Vector[SubscriptionInfo]]
  def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Map[String, Vector[SubscriptionInfo]]
}
