package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.domain.model.aggregate.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.{ MediatorDuty, RequestHandler }

import scala.concurrent.{ ExecutionContext, Future }

case class CancelOrderCommandHandler(orderRepository: OrderPaymentRepositoryOperations) extends RequestHandler[CancelOrderCommand, Boolean] {
  override def handle(command: CancelOrderCommand, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Boolean] = {
    orderRepository.getOrderAsync(command.orderId).flatMap {
      case None => Future.successful(false)
      case Some(o) =>
        val updatedOrder = o.order.setCancelledStatus()
        orderRepository.addOrUpdateOrderAsync(updatedOrder, o.paymentMethod).map(_ => true)
    }
  }
}
