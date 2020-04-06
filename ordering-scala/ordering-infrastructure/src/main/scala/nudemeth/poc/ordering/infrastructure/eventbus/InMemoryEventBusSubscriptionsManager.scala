package nudemeth.poc.ordering.infrastructure.eventbus

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.universe._

case class InMemoryEventBusSubscriptionsManager(private val handlers: mutable.Map[String, Vector[SubscriptionInfo]], private val eventTypes: ArrayBuffer[Class[_]]) extends EventBusSubscriptionsManager {

  override def addSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit = {
    val eventName = getEventKey[T]
    doAddSubscription(handlerTag.tpe.getClass, eventName)
  }

  override def removeSubscription[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Unit = {
    val handlerToRemove = findSubscriptionToRemove[T, TH]()
    val eventName = getEventKey[T]
    doRemoveHandler(eventName, handlerToRemove)
  }

  override def getEventKey[T]()(implicit eventTag: TypeTag[T]): String = {
    eventTag.tpe.getClass.getName
  }

  override def hasSubscriptionsForEvent[T <: IntegrationEvent]()(implicit eventTag: TypeTag[T]): Boolean = {
    val key = getEventKey[T]()
    hasSubscriptionsForEvent(key)
  }

  override def hasSubscriptionsForEvent(eventName: String): Boolean = {
    handlers.contains(eventName)
  }

  private def doAddSubscription(handlerType: Class[_], eventName: String): Unit = {
    if (!hasSubscriptionsForEvent(eventName)) {
      handlers + (eventName -> Vector(SubscriptionInfo(handlerType)))
    }
    if (handlers("eventName").exists(h => h.handlerType == handlerType)) {
      throw new IllegalArgumentException(s"Handler Type ${handlerType.getName} already registered for '$eventName'")
    }
  }

  private def doRemoveHandler(eventName: String, subsToRemove: Option[SubscriptionInfo]): Unit = {
    subsToRemove match {
      case None =>
      case Some(str) =>
        handlers(eventName) = handlers(eventName).filterNot(s => s.handlerType == str.handlerType)
        if (!handlers.exists(s => s._1 == eventName)) {
          handlers - eventName
          eventTypes.find(e => e.getName == eventName).map { e =>
            eventTypes - e
          }
          raiseOnEventRemoved(eventName)
        }
    }
  }

  private def raiseOnEventRemoved(eventName: String): Unit = {

  }

  private def findSubscriptionToRemove[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH]): Option[SubscriptionInfo] = {
    val eventName = getEventKey[T]()
    doFindSubscriptionToRemove(eventName, handlerTag.tpe.getClass)
  }

  private def doFindSubscriptionToRemove(eventName: String, handlerType: Class[_]): Option[SubscriptionInfo] = {
    if (!hasSubscriptionsForEvent(eventName)) {
      None
    }
    handlers(eventName).find(s => s.handlerType == handlerType)
  }
}
