package nudemeth.poc.ordering.domain.model.aggregate.order

import java.util.UUID

import nudemeth.poc.ordering.domain.model.{ RepositoryOperations, Transactions }

import scala.concurrent.Future

trait OrderPaymentRepositoryOperations extends RepositoryOperations[OrderPayment] {
  def getOrderAsync(id: UUID): Future[Option[OrderPayment]]
  def addOrUpdateOrderAsync(orderPayment: OrderPayment): Transactions[Unit]
}
