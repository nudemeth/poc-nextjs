package nudemeth.poc.ordering.api.application.query

import java.net.InetSocketAddress
import java.time.ZoneOffset
import java.util.UUID

import com.datastax.oss.driver.api.core.{ CqlIdentifier, CqlSession }
import nudemeth.poc.ordering.api.application.query.entity.{ CardTypeEntity, OrderByUserEntity, OrderEntity }
import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery extends OrderQueryable {

  def getOrdersByUserIdAsync2(id: UUID): Future[Vector[OrderSummary]] = {
    val session = CqlSession.builder()
      .addContactPoint(new InetSocketAddress("ordering-db", 9042))
      .withAuthCredentials("sa", "P@ssw0rd")
      .withKeyspace("ordering")
      .build()

    val result = autoClose(session) { session =>
      val query = session
        .prepare("SELECT order_id, order_date, status_name, total FROM order_by_buyer_id WHERE buyer_id = :buyerId")
        .bind().setUuid("buyerId", id)
      val rs = session.execute(query)
      Iterator.continually(rs.iterator().next()).map { r =>
        OrderSummary(
          r.getUuid(CqlIdentifier.fromCql("order_id")),
          r.getInstant(CqlIdentifier.fromCql("order_date")).atOffset(ZoneOffset.UTC),
          r.getString(CqlIdentifier.fromCql("status_name")),
          r.getInt(CqlIdentifier.fromCql("total")))
      }
        .toVector
    }
    Future.successful(result)
  }

  private def autoClose[A <: AutoCloseable, B](closeable: A)(func: A => B): B = {
    try {
      func(closeable)
    } finally {
      closeable.close()
    }
  }

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      m <- mapToViewModel(e)
    } yield m
  }

  override def getOrdersByUserIdAsync(userId: UUID): Future[Vector[OrderSummary]] = {
    for {
      e <- OrderDatabase.OrderByUserModel.getByBuyerId(userId)
      m <- mapToViewModels(e)
    } yield m
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    for {
      e <- OrderDatabase.CardTypeModel.list()
      m <- mapToViewModel(e)
    } yield m
  }

  override def deleteOrderAsync(id: UUID, userId: UUID): Future[Boolean] = {
    OrderDatabase.delete(id, userId).map(r => r.wasApplied())
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
