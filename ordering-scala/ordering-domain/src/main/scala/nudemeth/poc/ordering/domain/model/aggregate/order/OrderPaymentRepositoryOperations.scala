package nudemeth.poc.ordering.domain.model.aggregate.order

import java.util.UUID

import nudemeth.poc.ordering.domain.model.RepositoryOperations
import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod
import nudemeth.poc.ordering.util.mediator.Notification

import scala.concurrent.Future

trait OrderPaymentRepositoryOperations extends RepositoryOperations[OrderPayment] {
  def getOrderAsync(id: UUID): Future[Option[OrderPayment]]
  def addOrUpdateOrderAsync(order: Order, paymentMethod: PaymentMethod, domainEvents: Vector[Notification]): Future[Unit]
}
