package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

import nudemeth.poc.ordering.domain.model.{ RepositoryOperations, Transactions }

import scala.concurrent.Future

trait BuyerRepositoryOperations extends RepositoryOperations[Buyer] {
  def addOrUpdate(buyer: Buyer): Transactions[Unit]
  def find(buyerId: UUID): Future[Option[Buyer]]
}
