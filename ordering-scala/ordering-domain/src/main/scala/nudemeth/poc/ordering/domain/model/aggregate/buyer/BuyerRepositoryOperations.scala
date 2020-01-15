package nudemeth.poc.ordering.domain.model.aggregate.buyer

import java.util.UUID

import scala.concurrent.Future

trait BuyerRepositoryOperations {
  def addOrUpdate(buyer: Buyer): Future[Unit]
  def find(buyerId: UUID): Future[Option[Buyer]]
}
