package nudemeth.poc.ordering.api.application.domaineventhandler

import nudemeth.poc.ordering.api.application.integrationevent.OrderingIntegrationEventServiceOperations
import nudemeth.poc.ordering.api.application.integrationevent.event.OrderStatusChangedToCancelledIntegrationEvent
import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.model.aggregate.buyer.BuyerRepositoryOperations
import nudemeth.poc.ordering.domain.model.aggregate.order.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.NotificationHandler

import scala.concurrent.{ ExecutionContext, Future }

case class OrderCancelledDomainEventHandler(orderPaymentRepo: OrderPaymentRepositoryOperations, buyerRepo: BuyerRepositoryOperations, orderingIntegrationEventService: OrderingIntegrationEventServiceOperations) extends NotificationHandler[OrderCancelledDomainEvent] {
  override def handle(notification: OrderCancelledDomainEvent)(implicit executor: ExecutionContext): Future[Unit] = {
    for {
      order <- orderPaymentRepo.getOrderAsync(notification.order.orderId)
      buyer <- buyerRepo.find(order.get.order.buyerId)
      event = OrderStatusChangedToCancelledIntegrationEvent(order.get.order.orderId, order.get.order.orderStatus, buyer.get.name)
      result <- orderingIntegrationEventService.publishThroughEventBusAsync(event)
    } yield result
  }
}
