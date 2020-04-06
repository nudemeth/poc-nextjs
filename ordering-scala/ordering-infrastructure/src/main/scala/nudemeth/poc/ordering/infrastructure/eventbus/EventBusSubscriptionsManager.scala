package nudemeth.poc.ordering.infrastructure.eventbus

import scala.reflect.runtime.universe._

trait EventBusSubscriptionsManager {
  def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit
  def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit
  def getEventKey[T]()(implicit eventTag: TypeTag[T]): String
  def hasSubscriptionsForEvent[T <: IntegrationEvent]()(implicit eventTag: TypeTag[T]): Boolean
  def hasSubscriptionsForEvent(eventName: String): Boolean
}
