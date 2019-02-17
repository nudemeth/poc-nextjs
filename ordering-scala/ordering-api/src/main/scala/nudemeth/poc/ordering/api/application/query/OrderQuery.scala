package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.api.application.query.entity.{ CardTypeEntity, OrderByUserEntity, OrderEntity }
import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery extends OrderQueryable {

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      m <- mapToViewModel(e)
    } yield m
  }

  override def getOrdersByUserNameAsync(userId: UUID): Future[Vector[OrderSummary]] = {
    for {
      e <- OrderDatabase.OrderByUserModel.getByUserName(userId)
      m <- mapToViewModels(e)
    } yield m
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    for {
      e <- OrderDatabase.CardTypeModel.list()
      m <- mapToViewModel(e)
    } yield m
  }

  private def mapToViewModel(entities: Vector[CardTypeEntity]): Future[Vector[CardType]] = Future {
    entities.map(e => {
      CardType(e.name)
    })
  }

  private def mapToViewModels(entities: List[OrderByUserEntity]): Future[Vector[OrderSummary]] = Future {
    entities.map(e => {
      OrderSummary(
        e.orderId,
        e.orderDate,
        e.statusName,
        e.total)
    })
      .toVector
  }

  private def mapToViewModel(mbEntity: Option[OrderEntity]): Future[Option[Order]] = Future {
    mbEntity match {
      case None => None
      case Some(e) => Some(Order(
        e.orderId,
        e.orderDate,
        e.statusName,
        e.description,
        e.addressStreet,
        e.addressCity,
        e.addressZipCode,
        e.addressCountry,
        e.orderItems.map(ee => {
          OrderItem(ee._1, ee._2._1, ee._2._2, ee._2._3)
        }).toVector,
        e.orderItems.size))
    }
  }
}
