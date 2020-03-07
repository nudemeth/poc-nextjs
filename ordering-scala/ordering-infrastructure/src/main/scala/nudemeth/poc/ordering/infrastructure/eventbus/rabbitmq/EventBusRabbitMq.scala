package nudemeth.poc.ordering.infrastructure.eventbus.rabbitmq

import akka.actor.ActorRef
import com.newmotion.akka.rabbitmq.{ ChannelActor, CreateChannel }
import nudemeth.poc.ordering.infrastructure.eventbus.{ EventBusOperations, IntegrationEvent, IntegrationEventHandlerOperations }

case class EventBusRabbitMq(connectionActor: ActorRef) extends EventBusOperations {
  override def publish(integrationEvent: IntegrationEvent): Unit = {
    connectionActor ! CreateChannel(ChannelActor.props(), Some("ordering-eventbus-publisher-actor"))

  }

  override def subscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }

  override def unsubscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }
}
