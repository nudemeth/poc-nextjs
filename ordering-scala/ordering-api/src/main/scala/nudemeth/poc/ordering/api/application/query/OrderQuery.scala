package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery extends OrderQueryable {

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      d <- mapToDomain(e)
    } yield d
  }

  override def getOrdersByUserAsync(userId: UUID): Future[Vector[Order]] = {
    for {
      e <- OrderDatabase.OrderByUserModel.getByUserName(userId)
      d <- mapToViewModel(e)
    } yield d
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    for {
      e <- OrderDatabase.CardTypeModel.list()
      d <- mapToViewModel(e)
    } yield d
  }

  private def mapToViewModel(entities: Vector[CardTypeEntity]): Future[Vector[CardType]] = Future {
    /*entities.map(e => {
      CardType.withName(e.name)
    })*/
    Vector.empty[CardType]
  }

  private def mapToViewModel(entities: List[OrderEntity]): Future[Vector[Order]] = Future {
    entities.map(e => {
      Order(
        e.orderId,
        e.orderDate,
        e.statusName,
        e.description,
        e.addressStreet,
        e.addressCity,
        e.addressZipCode,
        e.addressCountry,
        Vector.empty[OrderItem],
        0)
    })
      .toVector
  }

  private def mapToDomain(entity: Option[OrderEntity]): Future[Option[Order]] = Future {
    entity.map(e => {
      Order(
        e.orderId,
        e.orderDate,
        e.statusName,
        e.description,
        e.addressStreet,
        e.addressCity,
        e.addressZipCode,
        e.addressCountry,
        Vector.empty[OrderItem],
        0)
    })
  }
}
