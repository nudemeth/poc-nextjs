package nudemeth.poc.ordering.api.application.query

import java.net.InetSocketAddress
import java.time.ZoneOffset
import java.util.UUID

import com.datastax.oss.driver.api.core.cql.{ AsyncResultSet, Row }
import com.datastax.oss.driver.api.core.{ CqlIdentifier, CqlSession }
import com.typesafe.config.ConfigFactory
import nudemeth.poc.ordering.api.application.query.entity.{ CardTypeEntity, OrderByUserEntity, OrderEntity }
import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.JavaConverters._
import scala.compat.java8.FutureConverters._

class OrderQuery extends OrderQueryable {

  private val config = ConfigFactory.load()
  private val session = CqlSession.builder()
    .addContactPoints(config.getStringList("cassandra.host").asScala.map(h => {
      new InetSocketAddress(h, 9042)
    }).asJavaCollection)
    .withAuthCredentials(config.getString("cassandra.username"), config.getString("cassandra.password"))
    .withKeyspace(config.getString("cassandra.keyspace"))
    .build()

  private val getOrdersByUserIdAsyncCql = "SELECT order_id, order_date, status_name, total FROM order_by_buyer_id WHERE buyer_id = :buyerId"

  override def finalize(): Unit = {
    if (!session.isClosed) {
      session.close()
    }
    super.finalize()
  }

  def getOrdersByUserIdAsync(id: UUID): Future[Vector[OrderSummary]] = {
    val query = session
      .prepare(getOrdersByUserIdAsyncCql)
      .bind()
      .setUuid("buyerId", id)

    session.executeAsync(query).toScala.map { r =>
      r.currentPage().asScala
    }.map { r =>
      buildOrderSummary(r)
    }
  }

  private def buildOrderSummary(rows: Iterable[Row]): Vector[OrderSummary] = {
    rows.map { r =>
      OrderSummary(
        r.getUuid(CqlIdentifier.fromCql("order_id")),
        r.getInstant(CqlIdentifier.fromCql("order_date")).atOffset(ZoneOffset.UTC),
        r.getString(CqlIdentifier.fromCql("status_name")),
        r.getInt(CqlIdentifier.fromCql("total")))
    }
      .toVector
  }

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      m <- mapToViewModel(e)
    } yield m
  }

  /*
  override def getOrdersByUserIdAsync2(userId: UUID): Future[Vector[OrderSummary]] = {
    for {
      e <- OrderDatabase.OrderByUserModel.getByBuyerId(userId)
      m <- mapToViewModels(e)
    } yield m
  }*/

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
