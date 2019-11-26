package nudemeth.poc.ordering.infrastructure.repository

import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod
import nudemeth.poc.ordering.domain.model.aggregate.order.Order

import scala.concurrent.Future

trait OrderRepositoryOperations {
  def getOrderAsync(id: UUID): Future[Option[(Order, PaymentMethod)]]
  def addOrUpdateOrderAsync(order: Order, paymentMethod: PaymentMethod): Future[Unit]
}
