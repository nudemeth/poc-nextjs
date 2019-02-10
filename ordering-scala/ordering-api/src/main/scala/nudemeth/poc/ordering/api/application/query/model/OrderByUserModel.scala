package nudemeth.poc.ordering.api.application.query.model

import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.keys.PartitionKey
import nudemeth.poc.ordering.api.application.query.entity.OrderByUserEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderByUserModel extends Table[OrderByUserModel, OrderByUserEntity] {
  override def tableName: String = "order_by_buyer_id"

  object buyer_Id extends Col[UUID] with PartitionKey
  object order_id extends Col[UUID] with ClusteringOrder
  object order_date extends Col[ZonedDateTime]
  object status_name extends Col[String]
  object total extends Col[Int]

  def getByUserName(userId: UUID): Future[List[OrderByUserEntity]] = {
    select
      .where(_.buyer_Id eqs userId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def saveOrUpdate(order: OrderByUserEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderByUserEntity): InsertQuery[OrderByUserModel, OrderByUserEntity, Specified, HNil] = {
    insert
      .value(_.buyer_Id, order.buyerId)
      .value(_.order_id, order.orderId)
      .value(_.order_date, order.orderDate)
      .value(_.status_name, order.statusName)
      .value(_.total, order.total)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteByUserIdAndId(userName: UUID, id: UUID): Future[ResultSet] = deleteByUserIdAndIdTransaction(userName, id).future()
  def deleteByUserIdAndIdTransaction(userId: UUID, id: UUID): DeleteQuery[OrderByUserModel, OrderByUserEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.buyer_Id eqs userId)
      .and(_.order_id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
