package nudemeth.poc.ordering.infrastructure.eventbus

import scala.reflect.runtime.universe._

trait EventBusSubscriptionsManager {
  def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](handlers: Map[String, Vector[SubscriptionInfo]])(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Map[String, Vector[SubscriptionInfo]]
  def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](handlers: Map[String, Vector[SubscriptionInfo]])(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Map[String, Vector[SubscriptionInfo]]
}
