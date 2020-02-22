package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.domain.model.aggregate.order.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.{ MediatorDuty, RequestHandler }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

case class ShipOrderCommandHandler(orderRepository: OrderPaymentRepositoryOperations) extends RequestHandler[ShipOrderCommand, Boolean] {
  override def handle(command: ShipOrderCommand, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Boolean] = {
    orderRepository.getOrderAsync(command.orderId).flatMap {
      case None => Future.successful(false)
      case Some(o) => o.setShippedStatus() match {
        case Failure(exception) => Future.successful(false)
        case Success(value) => orderRepository.addOrUpdateOrderAsync(value.order, value.paymentMethod, value.domainEvents).map(_ => true)
      }
    }
  }
}
