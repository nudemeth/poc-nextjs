package nudemeth.poc.ordering.api.application.integrationevent
import nudemeth.poc.ordering.infrastructure.eventbus.IntegrationEvent

import scala.concurrent.{ ExecutionContext, Future }

case class OrderingIntegrationEventService() extends OrderingIntegrationEventServiceOperations {
  override def publishThroughEventBusAsync(event: IntegrationEvent)(implicit executor: ExecutionContext): Future[Unit] = {
    saveEventAndOrderingContextChangesAsync(event)
  }

  private def saveEventAndOrderingContextChangesAsync(event: IntegrationEvent): Future[Unit] = {
    Future.unit
  }
}
