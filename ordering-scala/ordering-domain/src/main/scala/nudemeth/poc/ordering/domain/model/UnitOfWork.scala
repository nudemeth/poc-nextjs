package nudemeth.poc.ordering.domain.model

import scala.concurrent.Future

trait UnitOfWork {
  def saveChangeAsync(transactions: Transactions): Future[Int]
  def saveEntitiesAsync(transactions: Transactions): Future[Boolean]
}
