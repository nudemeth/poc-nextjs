package nudemeth.poc.ordering.api.application.query

import java.time.ZonedDateTime
import java.util.UUID

import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery extends OrderQueryable {

  val orders: Vector[Order] = Vector(
    Order(1, ZonedDateTime.now(), "InProgress", "abc", "aa", "Bangkok", "00000", "Thailand", Vector(OrderItem("XXX", 100, 200, "/xxx.png"), OrderItem("YYY", 200, 300, "/yyy.png")), 0),
    Order(2, ZonedDateTime.now(), "Pending", "abc", "aa", "Bangkok", "00000", "Thailand", Vector(OrderItem("ZZZ", 400, 500, "/zzz.png")), 0))

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    /*for {
      e <- OrderDatabase.OrderModel.getById(id)
      d <- mapToDomain(e)
    } yield d*/
    Future(orders.find(_.orderNumber == 1))
  }

  override def getOrdersByUserAsync(userName: String): Future[Vector[Order]] = {
    /*for {
      e <- OrderDatabase.OrderByUserModel.getByUserName(userName)
      d <- mapToDomains(e)
    } yield d*/
    Future(orders)
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    for {
      e <- OrderDatabase.CardTypeModel.list()
      d <- mapToDomain(e)
    } yield d
  }

  private def mapToDomain(entities: Vector[CardTypeEntity]): Future[Vector[CardType]] = Future {
    /*entities.map(e => {
      CardType.withName(e.name)
    })*/
    entities.map(e => CardType(e.name))
  }

  /*private def mapToDomains(entities: Vector[OrderEntity]): Future[Vector[Order]] = Future {
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
  }*/
}
