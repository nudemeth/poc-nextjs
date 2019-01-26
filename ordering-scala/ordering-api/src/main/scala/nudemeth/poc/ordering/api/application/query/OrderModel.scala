package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.keys.PrimaryKey
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderModel extends Table[OrderModel, OrderEntity] {
  override def tableName: String = "Order"

  object order_id extends Col[UUID] with PrimaryKey
  object order_date extends Col[ZonedDateTime]
  object description extends Col[String]
  object address_city extends Col[String]
  object address_country extends Col[String]
  object address_state extends OptionalCol[String]
  object address_street extends Col[String]
  object address_zip_code extends Col[String]
  object status_name extends Col[String]
  object product_name extends Col[String]
  object units extends Col[Int]
  object unit_price extends Col[Double]
  object picture_url extends Col[String]

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
      .value(_.product_name, order.productName)
      .value(_.units, order.units)
      .value(_.unit_price, order.unitPrice)
      .value(_.picture_url, order.pictureUrl)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteById(id: UUID): Future[ResultSet] = deleteByIdTransaction(id).future()
  def deleteByIdTransaction(id: UUID): DeleteQuery[OrderModel, OrderEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.order_id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
