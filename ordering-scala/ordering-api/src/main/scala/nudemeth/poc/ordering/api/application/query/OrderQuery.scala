package nudemeth.poc.ordering.api.application.query

import java.net.InetSocketAddress
import java.time.ZoneOffset
import java.util.UUID

import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.{ CqlIdentifier, CqlSession }
import com.typesafe.config.ConfigFactory
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

  private val getOrdersByUserIdCql = "SELECT order_id, order_date, status_name, total FROM order_by_buyer_id WHERE buyer_id = :buyerId"
  private val getCardTypesCql = "SELECT name FROM card_type"

  override def finalize(): Unit = {
    if (!session.isClosed) {
      session.close()
    }
    super.finalize()
  }

  def getOrdersByUserIdAsync(id: UUID): Future[Vector[OrderSummary]] = {
    val query = session
      .prepare(getOrdersByUserIdCql)
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
    }.toVector
  }

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    Future.successful(None)
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    val query = session
      .prepare(getCardTypesCql)
      .bind()

    session.executeAsync(query).toScala.map { r =>
      r.currentPage().asScala
    }.map { r =>
      buildCardTypes(r)
    }
  }

  private def buildCardTypes(rows: Iterable[Row]): Vector[CardType] = {
    rows.map { r =>
      CardType(
        r.getString(CqlIdentifier.fromCql("name")))
    }.toVector
  }
}
