package nudemeth.poc.ordering.infrastructure.repository

import java.time.ZoneOffset
import java.util.UUID

import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.UnitOfWork
import nudemeth.poc.ordering.domain.model.aggregate.buyer.{ Buyer, BuyerRepositoryOperations, CardType, PaymentMethod }
import nudemeth.poc.ordering.infrastructure.repository.entity.BuyerEntity
import nudemeth.poc.ordering.infrastructure.{ Connector, OrderingContext }

import scala.concurrent.Future

case class BuyerRepository(orderingContext: OrderingContext) extends BuyerRepositoryOperations {
  implicit val session: Session = Connector.connector.session

  override val unitOfWork: UnitOfWork = orderingContext

  override def addOrUpdate(buyer: Buyer): Future[Unit] = {
    orderingContext.BuyerTable.saveOrUpdate(
      BuyerEntity(
        buyer.buyerId,
        buyer.name,
        buyer.paymentMethods.map(pm => (
          pm.alias,
          pm.cardNumber,
          pm.cardSecurityNumber,
          pm.cardHolderName,
          pm.cardExpiration.atOffset(ZoneOffset.UTC),
          pm.cardType.toString))
          .toList))
      .map(_ => ())
  }

  override def find(buyerId: UUID): Future[Option[Buyer]] = {
    orderingContext.BuyerTable.getById(buyerId).map {
      case None => None
      case Some(e) => Some(Buyer(
        e.buyerId,
        e.name,
        e.paymentMethods.map(pm =>
          PaymentMethod(
            pm._1,
            pm._2,
            pm._3,
            pm._4,
            pm._5.toInstant,
            CardType.apply(pm._6)))
          .toVector, Vector()))
    }
  }
}
