package nudemeth.poc.ordering.infrastructure.idempotency.table

import java.time.OffsetDateTime
import java.util.UUID

import com.datastax.driver.core.ConsistencyLevel
import com.outworkers.phantom.builder.Specified
import com.outworkers.phantom.builder.query.InsertQuery
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8.indexed._
import com.outworkers.phantom.keys.PrimaryKey
import com.outworkers.phantom.{ ResultSet, Table }
import nudemeth.poc.ordering.infrastructure.idempotency.entity.ClientRequestEntity
import shapeless.HNil

import scala.concurrent.Future

abstract class ClientRequestTable extends Table[ClientRequestTable, ClientRequestEntity] {
  override def tableName: String = "client_request"

  object requestId extends Col[UUID] with PrimaryKey { override lazy val name = "request_id" }
  object name extends Col[String]
  object time extends Col[OffsetDateTime]

  def getById(requestId: UUID): Future[Option[ClientRequestEntity]] = {
    select
      .where(_.requestId eqs requestId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def saveOrUpdate(request: ClientRequestEntity): Future[ResultSet] = saveOrUpdateTransaction(request).future()
  def saveOrUpdateTransaction(request: ClientRequestEntity): InsertQuery[ClientRequestTable, ClientRequestEntity, Specified, HNil] = {
    insert
      .value(_.requestId, request.id)
      .value(_.name, request.name)
      .value(_.time, request.time)
      .consistencyLevel_=(ConsistencyLevel.ALL)
  }
}
