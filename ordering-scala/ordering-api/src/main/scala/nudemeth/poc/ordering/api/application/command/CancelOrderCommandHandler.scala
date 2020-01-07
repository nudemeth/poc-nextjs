package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.api.infrastructure.mediator.{MediatorDuty, RequestHandler}
import nudemeth.poc.ordering.infrastructure.repository.OrderRepositoryOperations

import scala.concurrent.{ExecutionContext, Future}

case class CancelOrderCommandHandler(orderRepository: OrderRepositoryOperations) extends RequestHandler[CancelOrderCommand, Boolean] {
  override def handle(command: CancelOrderCommand, mediator: MediatorDuty): Future[Boolean] = {
    val orderToUpdate = orderRepository.getOrderAsync(command.orderId)
    orderToUpdate.flatMap { o =>
      if (o.isEmpty) {
        Future.successful(false)
      } else {
        val updatedOrder = o.get._1.setCancelledStatus()
        orderRepository.addOrUpdateOrderAsync(updatedOrder, o.get._2).map(_ => true)
      }
    }
  }
}
