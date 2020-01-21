package nudemeth.poc.ordering.domain.model.aggregate.order

import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod

import scala.concurrent.Future

trait OrderPaymentRepositoryOperations {
  def getOrderAsync(id: UUID): Future[Option[OrderPayment]]
  def addOrUpdateOrderAsync(order: Order, paymentMethod: PaymentMethod): Future[Unit]
}
