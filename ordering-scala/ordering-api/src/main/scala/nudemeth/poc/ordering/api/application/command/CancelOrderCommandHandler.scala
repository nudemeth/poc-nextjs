package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.domain.model.aggregate.order.OrderPaymentRepositoryOperations
import nudemeth.poc.ordering.util.mediator.{ MediatorDuty, RequestHandler }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

case class CancelOrderCommandHandler(orderRepository: OrderPaymentRepositoryOperations) extends RequestHandler[CancelOrderCommand, Boolean] {
  override def handle(command: CancelOrderCommand, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Boolean] = {
    orderRepository.getOrderAsync(command.orderId).flatMap {
      case None => Future.successful(false)
      case Some(o) => o.setCancelledStatus() match {
        case Failure(ex) => Future.successful(false)
        case Success(orderPayment) =>
          val transactions = orderRepository.addOrUpdateOrderAsync(orderPayment)
          orderRepository.unitOfWork.saveEntitiesAsync(transactions).map(_ => true)
      }
    }
  }
}
