package nudemeth.poc.ordering.infrastructure.eventbus

import scala.collection.mutable
import scala.reflect.runtime.universe._

case class InMemoryEventBusSubscriptionsManager(private val handlers: mutable.Map[String, Vector[SubscriptionInfo]]) extends EventBusSubscriptionsManager {

  override def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit = {
    val eventName = getEventKey[T]
    if (!hasSubscriptionsForEvent[T]()) {
      handlers + (eventName -> Vector(SubscriptionInfo(handlerTag.tpe.getClass)))
    }
    val handlerType = handlerTag.tpe.getClass
    if (handlers("eventName").exists(h => h.handlerType == handlerType)) {
      throw new IllegalArgumentException(s"Handler Type ${handlerType.getName} already registered for '$eventName'")
    }
  }

  override def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit = {
    val eventName = getEventKey[T]
    if (handlers.contains(eventName)) {
      handlers - eventName
    }
  }

  override def getEventKey[T]()(implicit eventTag: TypeTag[T]): String = {
    eventTag.tpe.getClass.getName
  }

  override def hasSubscriptionsForEvent[T <: IntegrationEvent]()(implicit eventTag: TypeTag[T]): Boolean = {
    val key = getEventKey[T]()
    handlers.contains(key)
  }
}
