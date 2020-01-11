package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.api.infrastructure.mediator.{ MediatorDuty, RequestHandler }
import nudemeth.poc.ordering.infrastructure.repository.OrderRepositoryOperations

import scala.concurrent.{ ExecutionContext, Future }

case class ShipOrderCommandHandler(orderRepository: OrderRepositoryOperations) extends RequestHandler[ShipOrderCommand, Boolean] {
  override def handle(command: ShipOrderCommand, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Boolean] = {
    orderRepository.getOrderAsync(command.orderId).flatMap {
      case None => Future.successful(false)
      case Some(o) =>
        val updatedOrder = o.order.setShippedStatus()
        orderRepository.addOrUpdateOrderAsync(updatedOrder, o.paymentMethod).map(_ => true)
    }
  }
}
