package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery extends OrderQueryable {

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      d <- mapToViewModel(e)
    } yield d
  }

  override def getOrdersByUserAsync(userId: UUID): Future[Vector[Order]] = {
    for {
      e <- OrderDatabase.OrderByUserModel.getByUserName(userId)
      d <- mapToViewModels(e)
    } yield d
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    for {
      e <- OrderDatabase.CardTypeModel.list()
      d <- mapToViewModel(e)
    } yield d
  }

  private def mapToViewModel(entities: Vector[CardTypeEntity]): Future[Vector[CardType]] = Future {
    entities.map(e => {
      CardType(e.name)
    })
  }

  private def mapToViewModels(entities: List[OrderEntity]): Future[Vector[Order]] = Future {
    entities
      .groupBy(e => {
        Order(
          e.orderId,
          e.orderDate,
          e.statusName,
          e.description,
          e.addressStreet,
          e.addressCity,
          e.addressZipCode,
          e.addressCountry,
          Vector[OrderItem](),
          0)
      })
      .map(e => {
        Order(
          e._1.orderNumber,
          e._1.date,
          e._1.status,
          e._1.description,
          e._1.street,
          e._1.city,
          e._1.zipCode,
          e._1.country,
          e._2.map(ee => {
            OrderItem(
              ee.productName,
              ee.units,
              ee.unitPrice,
              ee.pictureUrl)
          }).toVector,
          e._2.size)
      })
      .toVector
  }

  private def mapToViewModel(entities: List[OrderEntity]): Future[Option[Order]] = {
    mapToViewModels(entities).map(e => {
      Some(e(0))
    })
  }
}
