package nudemeth.poc.ordering.infrastructure.repository.table

import java.time.OffsetDateTime
import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import com.outworkers.phantom.keys.PartitionKey
import nudemeth.poc.ordering.infrastructure.repository.entity.OrderByBuyerEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderByBuyerTable extends Table[OrderByBuyerTable, OrderByBuyerEntity] {
  override def tableName: String = "order_by_buyer_id"

  object buyerId extends Col[UUID] with PartitionKey { override lazy val name = "buyer_id" }
  object orderId extends Col[UUID] with ClusteringOrder { override lazy val name = "order_id" }
  object orderDate extends Col[OffsetDateTime] { override lazy val name = "order_date" }
  object statusName extends Col[String] { override lazy val name = "status_name" }
  object total extends Col[Int]

  def getByBuyerId(userId: UUID): Future[List[OrderByBuyerEntity]] = {
    select
      .where(_.buyerId eqs userId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def saveOrUpdate(order: OrderByBuyerEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderByBuyerEntity): InsertQuery[OrderByBuyerTable, OrderByBuyerEntity, Specified, HNil] = {
    insert
      .value(_.buyerId, order.buyerId)
      .value(_.orderId, order.orderId)
      .value(_.orderDate, order.orderDate)
      .value(_.statusName, order.statusName)
      .value(_.total, order.total)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteByUserIdAndId(userName: UUID, id: UUID): Future[ResultSet] = deleteByUserIdAndIdTransaction(userName, id).future()
  def deleteByUserIdAndIdTransaction(userId: UUID, id: UUID): DeleteQuery[OrderByBuyerTable, OrderByBuyerEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.buyerId eqs userId)
      .and(_.orderId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
