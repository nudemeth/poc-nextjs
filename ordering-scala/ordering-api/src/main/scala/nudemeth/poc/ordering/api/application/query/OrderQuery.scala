package nudemeth.poc.ordering.api.application.query

import java.net.InetSocketAddress
import java.time.ZoneOffset
import java.util.UUID

import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.data.TupleValue
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
  private val getOrderCql = """SELECT
                              | order_id, order_date, description, address_city, address_country,
                              | address_state, address_street, address_zip_code, status_name, order_items
                              | FROM order_by_id
                              | WHERE order_id = :orderId""".stripMargin.stripLineEnd
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

    for {
      result <- session.executeAsync(query).toScala
    } yield for {
      row <- result.currentPage().asScala.toVector
      orderSummary = OrderSummary(
        row.getUuid(CqlIdentifier.fromCql("order_id")),
        row.getInstant(CqlIdentifier.fromCql("order_date")).atOffset(ZoneOffset.UTC),
        row.getString(CqlIdentifier.fromCql("status_name")),
        row.getInt(CqlIdentifier.fromCql("total")))
    } yield orderSummary
  }

  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    val query = session
      .prepare(getOrderCql)
      .bind()
      .setUuid("orderId", id)

    for {
      result <- session.executeAsync(query).toScala
    } yield for {
      row <- Option(result.one())
      orderItems = getOrderItems(row)
      order = Order(
        row.getUuid(CqlIdentifier.fromCql("order_id")),
        row.getInstant(CqlIdentifier.fromCql("order_id")).atOffset(ZoneOffset.UTC),
        row.getString(CqlIdentifier.fromCql("status_name")),
        row.getString(CqlIdentifier.fromCql("description")),
        row.getString(CqlIdentifier.fromCql("address_street")),
        row.getString(CqlIdentifier.fromCql("address_city")),
        row.getString(CqlIdentifier.fromCql("address_zip_code")),
        row.getString(CqlIdentifier.fromCql("address_country")),
        orderItems)
    } yield order
  }

  override def getCardTypesAsync: Future[Vector[CardType]] = {
    val query = session
      .prepare(getCardTypesCql)
      .bind()

    for {
      result <- session.executeAsync(query).toScala
    } yield for {
      row <- result.currentPage().asScala.toVector
      cardTypes = CardType(row.getString(CqlIdentifier.fromCql("name")))
    } yield cardTypes
  }

  private def getOrderItems(row: Row): Vector[OrderItem] = {
    row.getMap(CqlIdentifier.fromCql("order_items"), classOf[String], classOf[TupleValue]).asScala.map { o =>
      OrderItem(
        o._1,
        o._2.getInt(0),
        o._2.getBigDecimal(1),
        o._2.getString(2))
    }.toVector
  }
}
