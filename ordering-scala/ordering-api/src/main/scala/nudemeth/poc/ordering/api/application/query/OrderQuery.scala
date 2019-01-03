package nudemeth.poc.ordering.api.application.query

import java.time.Instant
import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.order.{ Address, Order }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery(connStr: String) extends OrderQueryable {
  override def GetOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- Database.OrderModel.getById(id)
      d <- mapToDomain(e)
    } yield d
  }

  override def GetOrdersFromUserAsync(userName: String): Future[List[Order]] = {
    for {
      e <- Database.OrderByUserModel.getByUserName(userName)
      d <- mapToDomains(e)
    } yield d
  }

  override def GetCardTypesAsync(): Future[Order] = {
    Future.successful(Order("", "", None, 0, "", "", "", Some(Instant.now)))
  }

  private def mapToDomains(entities: List[OrderEntity]): Future[List[Order]] = Future {
    entities.map(e => {
      Order(
        e.userId,
        e.userName,
        Some(Address(e.street, e.city, e.state, e.country, e.zipCode)),
        e.cardTypeId,
        e.cardNumber,
        e.cardSecurityNumber,
        e.cardHolderName,
        e.cardExpiration.map(c => c.toInstant),
        e.buyerId,
        e.paymentMethodId)
    })
  }

  private def mapToDomain(entity: Option[OrderEntity]): Future[Option[Order]] = Future {
    entity.map(e => {
      Order(
        e.userId,
        e.userName,
        Some(Address(e.street, e.city, e.state, e.country, e.zipCode)),
        e.cardTypeId,
        e.cardNumber,
        e.cardSecurityNumber,
        e.cardHolderName,
        e.cardExpiration.map(c => c.toInstant),
        e.buyerId,
        e.paymentMethodId)
    })
  }
}