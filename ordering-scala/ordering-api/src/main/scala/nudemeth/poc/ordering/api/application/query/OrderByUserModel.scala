package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.keys.PartitionKey
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderByUserModel extends Table[OrderByUserModel, OrderEntity] {
  override def tableName: String = "order_by_buyer_id"

  object buyer_Id extends Col[UUID] with PartitionKey
  object order_id extends Col[UUID] with ClusteringOrder
  object order_date extends Col[ZonedDateTime]
  object address_city extends Col[String]
  object address_country extends Col[String]
  object address_state extends OptionalCol[String]
  object address_street extends Col[String]
  object address_zip_code extends Col[String]
  object status_name extends Col[String]
  object product_name extends Col[String]
  object units extends Col[Int]
  object unit_price extends Col[Double]

  def getByUserName(userId: UUID): Future[List[OrderEntity]] = {
    select
      .where(_.buyer_Id eqs userId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderEntity): InsertQuery[OrderByUserModel, OrderEntity, Specified, HNil] = {
    insert
      .value(_.buyer_Id, order.buyerId)
      .value(_.order_id, order.orderId)
      .value(_.order_date, order.orderDate)
      .value(_.address_city, order.addressCity)
      .value(_.address_country, order.addressCountry)
      .value(_.address_state, order.addressState)
      .value(_.address_street, order.addressStreet)
      .value(_.address_zip_code, order.addressZipCode)
      .value(_.status_name, order.statusName)
      .value(_.product_name, order.productName)
      .value(_.units, order.units)
      .value(_.unit_price, order.unitPrice)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteByUserIdAndId(userName: UUID, id: UUID): Future[ResultSet] = deleteByUserIdAndIdTransaction(userName, id).future()
  def deleteByUserIdAndIdTransaction(userId: UUID, id: UUID): DeleteQuery[OrderByUserModel, OrderEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.buyer_Id eqs userId)
      .and(_.order_id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
