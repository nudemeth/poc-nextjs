package nudemeth.poc.ordering.api.application.integrationevent
import nudemeth.poc.ordering.infrastructure.eventbus.{ EventBusOperations, IntegrationEvent }

import scala.concurrent.{ ExecutionContext, Future }

case class OrderingIntegrationEventService(eventBus: EventBusOperations) extends OrderingIntegrationEventServiceOperations {
  override def publishThroughEventBusAsync(event: IntegrationEvent)(implicit executor: ExecutionContext): Future[Unit] = {
    saveEventAndOrderingContextChangesAsync(event).map { _ =>
      eventBus.publish(event)
    }
  }

  private def saveEventAndOrderingContextChangesAsync(event: IntegrationEvent): Future[Unit] = {
    Future.unit
  }
}
