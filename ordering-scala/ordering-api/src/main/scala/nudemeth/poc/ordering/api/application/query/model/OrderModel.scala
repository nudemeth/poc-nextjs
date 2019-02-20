package nudemeth.poc.ordering.api.application.query.model

import java.time.ZonedDateTime
import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import nudemeth.poc.ordering.api.application.query.entity.OrderEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderModel extends Table[OrderModel, OrderEntity] {
  override def tableName: String = "order_by_id"

  object order_id extends Col[UUID] with PrimaryKey
  object order_date extends Col[ZonedDateTime]
  object description extends Col[String]
  object address_city extends Col[String]
  object address_country extends Col[String]
  object address_state extends OptionalCol[String]
  object address_street extends Col[String]
  object address_zip_code extends Col[String]
  object status_name extends Col[String]
  object order_items extends MapColumn[String, (Int, Double, String)] //{product_name, {units, unit_price, picture_url}}

  def getById(id: UUID): Future[Option[OrderEntity]] = {
    select
      .where(_.order_id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderEntity): InsertQuery[OrderModel, OrderEntity, Specified, HNil] = {
    insert
      .value(_.order_id, order.orderId)
      .value(_.order_date, order.orderDate)
      .value(_.description, order.description)
      .value(_.address_city, order.addressCity)
      .value(_.address_country, order.addressCountry)
      .value(_.address_state, order.addressState)
      .value(_.address_street, order.addressStreet)
      .value(_.address_zip_code, order.addressZipCode)
      .value(_.status_name, order.statusName)
      .value(_.order_items, order.orderItems)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteById(id: UUID): Future[ResultSet] = deleteByIdTransaction(id).future()
  def deleteByIdTransaction(id: UUID): DeleteQuery[OrderModel, OrderEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.order_id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
