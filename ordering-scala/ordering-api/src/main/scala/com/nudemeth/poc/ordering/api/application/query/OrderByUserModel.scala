package com.nudemeth.poc.ordering.api.application.query

import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.Specified
import com.outworkers.phantom.builder.query.InsertQuery
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.keys.PartitionKey
import shapeless.HNil

import scala.concurrent.Future

abstract class OrderByUserModel extends Table[OrderByUserModel, OrderEntity] {
  override def tableName: String = "OrderByUser"

  object id extends Col[UUID] with ClusteringOrder
  object userId extends Col[String]
  object userName extends Col[String] with PartitionKey
  object street extends Col[String]
  object city extends Col[String]
  object state extends OptionalCol[String]
  object country extends Col[String]
  object zipCode extends Col[String]
  object cardTypeId extends Col[Int]
  object cardNumber extends Col[String]
  object cardSecurityNumber extends Col[String]
  object cardHolderName extends Col[String]
  object cardExpiration extends OptionalCol[ZonedDateTime]
  object buyerId extends OptionalCol[Int]
  object paymentMethodId extends OptionalCol[Int]

  def getByUserName(userName: String): Future[List[OrderEntity]] = {
    select
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = saveOrUpdateTransaction(order).future()
  def saveOrUpdateTransaction(order: OrderEntity): InsertQuery[OrderByUserModel, OrderEntity, Specified, HNil] = {
    insert
      .value(_.id, order.id)
      .value(_.userId, order.userId)
      .value(_.userName, order.userName)
      .value(_.city, order.city)
      .value(_.state, order.state)
      .value(_.country, order.country)
      .value(_.zipCode, order.zipCode)
      .value(_.cardTypeId, order.cardTypeId)
      .value(_.cardNumber, order.cardNumber)
      .value(_.cardSecurityNumber, order.cardSecurityNumber)
      .value(_.cardHolderName, order.cardHolderName)
      .value(_.cardExpiration, order.cardExpiration)
      .value(_.buyerId, order.buyerId)
      .value(_.paymentMethodId, order.paymentMethodId)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteByUserName(userName: String): Future[ResultSet] = {
    delete
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
}
