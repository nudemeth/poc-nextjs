package nudemeth.poc.ordering.infrastructure.repository

import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.buyer.{ Buyer, BuyerRepositoryOperations }

import scala.concurrent.Future

case class BuyerRepository() extends BuyerRepositoryOperations {
  override def addOrUpdate(buyer: Buyer): Future[Unit] = {
    Future.unit
  }

  override def find(buyerId: UUID): Future[Option[Buyer]] = {
    Future.successful(None)
  }
}
