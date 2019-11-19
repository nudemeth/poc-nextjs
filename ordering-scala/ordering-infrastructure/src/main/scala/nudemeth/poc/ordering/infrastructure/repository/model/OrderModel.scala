package nudemeth.poc.ordering.infrastructure.repository.model

import java.time.OffsetDateTime
import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import nudemeth.poc.ordering.infrastructure.repository.entity.OrderEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderModel extends Table[OrderModel, OrderEntity] {
  override def tableName: String = "order_by_id"

  object orderId extends Col[UUID] with PrimaryKey { override lazy val name = "order_id" }
  object orderDate extends Col[OffsetDateTime] { override lazy val name = "order_date" }
  object description extends Col[String]
  object addressCity extends Col[String] { override lazy val name = "address_city" }
  object addressCountry extends Col[String] { override lazy val name = "address_country" }
  object addressState extends OptionalCol[String] { override lazy val name = "address_state" }
  object addressStreet extends Col[String] { override lazy val name = "address_street" }
  object addressZipCode extends Col[String] { override lazy val name = "address_zip_code" }
  object statusName extends Col[String] { override lazy val name = "status_name" }
  object orderItems extends MapColumn[String, (Int, Double, String)] { override lazy val name = "order_items" }

  def getById(id: UUID): Future[Option[OrderEntity]] = {
    select
      .where(_.orderId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderEntity): InsertQuery[OrderModel, OrderEntity, Specified, HNil] = {
    insert
      .value(_.orderId, order.orderId)
      .value(_.orderDate, order.orderDate)
      .value(_.description, order.description)
      .value(_.addressCity, order.addressCity)
      .value(_.addressCountry, order.addressCountry)
      .value(_.addressState, order.addressState)
      .value(_.addressStreet, order.addressStreet)
      .value(_.addressZipCode, order.addressZipCode)
      .value(_.statusName, order.statusName)
      .value(_.orderItems, order.orderItems)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteById(id: UUID): Future[ResultSet] = deleteByIdTransaction(id).future()
  def deleteByIdTransaction(id: UUID): DeleteQuery[OrderModel, OrderEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.orderId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
