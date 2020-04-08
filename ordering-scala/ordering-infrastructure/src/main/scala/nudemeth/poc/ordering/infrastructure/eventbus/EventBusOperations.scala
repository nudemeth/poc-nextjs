package nudemeth.poc.ordering.infrastructure.eventbus

import scala.reflect.runtime.universe._

trait EventBusOperations {
  def publish(event: IntegrationEvent)
  def subscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH])
  def unsubscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]]()(implicit eventTag: TypeTag[T], handlerTag: TypeTag[TH])
}
