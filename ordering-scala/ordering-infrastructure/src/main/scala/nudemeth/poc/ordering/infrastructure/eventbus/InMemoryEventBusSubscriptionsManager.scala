package nudemeth.poc.ordering.infrastructure.eventbus

import scala.reflect.runtime.universe._

case class InMemoryEventBusSubscriptionsManager() extends EventBusSubscriptionsManager {

  override def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](handlers: Map[String, Vector[SubscriptionInfo]])(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Map[String, Vector[SubscriptionInfo]] = {
    handlers + ("" -> Vector())
  }

  override def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](handlers: Map[String, Vector[SubscriptionInfo]])(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Map[String, Vector[SubscriptionInfo]] = {
    handlers + ("" -> Vector())
  }
}
