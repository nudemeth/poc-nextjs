package nudemeth.poc.ordering.infrastructure.eventbus

case class InMemoryEventBusSubscriptionsManager() extends EventBusSubscriptionsManager {

  override def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Map[String, Vector[SubscriptionInfo]] = {
    Map()
  }

  override def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Map[String, Vector[SubscriptionInfo]] = {
    Map()
  }
}
