package nudemeth.poc.ordering.infrastructure.repository

import com.outworkers.phantom.builder.Unspecified
import com.outworkers.phantom.builder.batch.BatchQuery
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.Transactions
import nudemeth.poc.ordering.infrastructure.Connector
import nudemeth.poc.ordering.util.mediator.Notification

import scala.concurrent.Future

case class CassandraTransactions[T](batchQuery: BatchQuery[Unspecified], mapToResult: ResultSet => T, domainEvents: Vector[Notification]) extends Transactions[T] {
  implicit val session: Session = Connector.connector.session

  override def execute(): Future[T] = {
    batchQuery.future().map(mapToResult)
  }
}
