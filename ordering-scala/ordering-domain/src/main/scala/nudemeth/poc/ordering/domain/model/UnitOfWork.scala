package nudemeth.poc.ordering.domain.model

import scala.concurrent.Future

trait UnitOfWork {
  def saveChangesAsync[T](transactions: Transactions[T]): Future[T]
  def saveEntitiesAsync[T](transactions: Transactions[T]): Future[Boolean]
}
