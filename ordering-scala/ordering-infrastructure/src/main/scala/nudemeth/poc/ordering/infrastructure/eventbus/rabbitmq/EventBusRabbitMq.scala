package nudemeth.poc.ordering.infrastructure.eventbus.rabbitmq

import nudemeth.poc.ordering.infrastructure.eventbus.{ EventBusOperations, IntegrationEvent, IntegrationEventHandlerOperations }

case class EventBusRabbitMq() extends EventBusOperations {
  override def publish(integrationEvent: IntegrationEvent): Unit = {

  }

  override def subscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }

  override def unsubscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }
}
