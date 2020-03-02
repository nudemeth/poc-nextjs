package nudemeth.poc.ordering.infrastructure.repository.table

import java.time.OffsetDateTime
import java.util.UUID

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.Specified
import com.outworkers.phantom.builder.query.InsertQuery
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import nudemeth.poc.ordering.infrastructure.repository.entity.BuyerEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class BuyerTable extends Table[BuyerTable, BuyerEntity] {
  override def tableName: String = "buyer"

  object buyerId extends Col[UUID] with PrimaryKey { override lazy val name = "buyer_id" }
  object name extends Col[String]
  object paymentMethods extends ListColumn[(String, String, String, String, OffsetDateTime, String)] { override lazy val name = "payment_methods" }

  def getById(id: UUID): Future[Option[BuyerEntity]] = {
    select
      .where(_.buyerId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveOrUpdate(buyer: BuyerEntity): Future[ResultSet] = {
    saveOrUpdateTransaction(buyer).future()
  }

  def saveOrUpdateTransaction(buyer: BuyerEntity): InsertQuery[BuyerTable, BuyerEntity, Specified, HNil] = {
    insert
      .value(_.buyerId, buyer.buyerId)
      .value(_.name, buyer.name)
      .value(_.paymentMethods, buyer.paymentMethods)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }

  def deleteById(id: UUID): Future[ResultSet] = {
    delete
      .where(_.buyerId eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

}
