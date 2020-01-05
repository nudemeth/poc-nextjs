package nudemeth.poc.ordering.infrastructure.repository.table

import java.time.OffsetDateTime
import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.{ Chainned, Specified, Unlimited, Unordered }
import com.outworkers.phantom.builder.query.{ DeleteQuery, InsertQuery }
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import nudemeth.poc.ordering.infrastructure.repository.entity.OrderByIdEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderByIdTable extends Table[OrderByIdTable, OrderByIdEntity] {
  override def tableName: String = "order_by_id"

  object orderId extends Col[UUID] with PrimaryKey { override lazy val name = "order_id" }
  object buyerId extends Col[UUID] { override lazy val name = "buyer_id" }
  object orderDate extends Col[OffsetDateTime] { override lazy val name = "order_date" }
  object description extends OptionalCol[String]
  object addressCity extends Col[String] { override lazy val name = "address_city" }
  object addressCountry extends Col[String] { override lazy val name = "address_country" }
  object addressState extends OptionalCol[String] { override lazy val name = "address_state" }
  object addressStreet extends Col[String] { override lazy val name = "address_street" }
  object addressZipCode extends Col[String] { override lazy val name = "address_zip_code" }
  object statusName extends Col[String] { override lazy val name = "status_name" }
  object paymentMethodAlias extends Col[String] { override lazy val name = "payment_method_alias" }
  object paymentMethodCardNumber extends Col[String] { override lazy val name = "payment_method_card_number" }
  object paymentMethodCardSecurityNumber extends Col[String] { override lazy val name = "payment_method_card_security_number" }
  object paymentMethodCardHolderName extends Col[String] { override lazy val name = "payment_method_card_holder_name" }
  object paymentMethodCardExpiration extends Col[OffsetDateTime] { override lazy val name = "payment_method_card_expiration" }
  object paymentMethodCardType extends Col[String] { override lazy val name = "payment_method_card_type" }
  object orderItems extends MapColumn[UUID, (String, String, BigDecimal, BigDecimal, Int)] { override lazy val name = "order_items" }

  def getById(id: UUID): Future[Option[OrderByIdEntity]] = {
    select
      .where(_.orderId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveOrUpdate(order: OrderByIdEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderByIdEntity): InsertQuery[OrderByIdTable, OrderByIdEntity, Specified, HNil] = {
    insert
      .value(_.orderId, order.orderId)
      .value(_.buyerId, order.buyerId)
      .value(_.orderDate, order.orderDate)
      .value(_.description, order.description)
      .value(_.addressCity, order.addressCity)
      .value(_.addressCountry, order.addressCountry)
      .value(_.addressState, order.addressState)
      .value(_.addressStreet, order.addressStreet)
      .value(_.addressZipCode, order.addressZipCode)
      .value(_.statusName, order.statusName)
      .value(_.paymentMethodAlias, order.paymentMethodAlias)
      .value(_.paymentMethodCardNumber, order.paymentMethodCardNumber)
      .value(_.paymentMethodCardSecurityNumber, order.paymentMethodCardSecurityNumber)
      .value(_.paymentMethodCardHolderName, order.paymentMethodCardHolderName)
      .value(_.paymentMethodCardExpiration, order.paymentMethodCardExpiration) //TODO: Can be removed?
      .value(_.paymentMethodCardType, order.paymentMethodCardType)
      .value(_.orderItems, order.orderItems)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteById(id: UUID): Future[ResultSet] = deleteByIdTransaction(id).future()
  def deleteByIdTransaction(id: UUID): DeleteQuery[OrderByIdTable, OrderByIdEntity, Unlimited, Unordered, Specified, Chainned, HNil] = {
    delete
      .where(_.orderId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
  }
}
