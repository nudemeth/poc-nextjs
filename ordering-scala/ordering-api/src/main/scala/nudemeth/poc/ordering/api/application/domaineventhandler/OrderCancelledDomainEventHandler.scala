package nudemeth.poc.ordering.api.application.domaineventhandler

import nudemeth.poc.ordering.api.application.integrationevent.OrderingIntegrationEventServiceOperations
import nudemeth.poc.ordering.api.application.integrationevent.event.OrderStatusChangedToCancelledIntegrationEvent
import nudemeth.poc.ordering.domain.event.OrderCancelledDomainEvent
import nudemeth.poc.ordering.domain.model.aggregate.buyer.BuyerRepositoryOperations
import nudemeth.poc.ordering.domain.model.aggregate.order.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.{ MediatorDuty, NotificationHandler }

import scala.concurrent.{ ExecutionContext, Future }

case class OrderCancelledDomainEventHandler(orderPaymentRepo: OrderPaymentRepositoryOperations, buyerRepo: BuyerRepositoryOperations, orderingIntegrationEventService: OrderingIntegrationEventServiceOperations) extends NotificationHandler[OrderCancelledDomainEvent] {
  override def handle(notification: OrderCancelledDomainEvent, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Unit] = {
    for {
      maybeOrderPayment <- orderPaymentRepo.getOrderAsync(notification.order.orderId)
    } yield for {
      orderPayment <- maybeOrderPayment
    } yield for {
      maybeBuyer <- buyerRepo.find(orderPayment.order.buyerId)
    } yield for {
      buyer <- maybeBuyer
      event = new OrderStatusChangedToCancelledIntegrationEvent(orderPayment.order.orderId, orderPayment.order.orderStatus, buyer.name)
    } yield for {
      result <- orderingIntegrationEventService.publishThroughEventBusAsync(event)
    } yield result
  }
}
