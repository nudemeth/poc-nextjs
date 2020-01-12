package nudemeth.poc.ordering.api.application.domaineventhandler

import nudemeth.poc.ordering.api.application.integrationevent.OrderingIntegrationEventServiceOperations
import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.model.aggregate.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.NotificationHandler

import scala.concurrent.{ ExecutionContext, Future }

case class OrderCancelledDomainEventHandler(orderPaymentRepo: OrderPaymentRepositoryOperations, orderingIntegrationEventService: OrderingIntegrationEventServiceOperations) extends NotificationHandler[OrderCancelledDomainEvent] {
  override def handle(notification: OrderCancelledDomainEvent)(implicit executor: ExecutionContext): Future[Unit] = {
    //val order = orderPaymentRepo.getOrderAsync(notification.order.orderId)
    //orderingIntegrationEventService.publishThroughEventBusAsync()
    Future.unit
  }
}
