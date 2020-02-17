package nudemeth.poc.ordering.infrastructure

import com.outworkers.phantom.builder.Unspecified
import com.outworkers.phantom.builder.batch.BatchQuery
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.{ Transactions, UnitOfWork }
import nudemeth.poc.ordering.infrastructure.idempotency.table._
import nudemeth.poc.ordering.infrastructure.repository.table._
import nudemeth.poc.ordering.util.mediator.MediatorDuty

import scala.concurrent.Future

class OrderingContext(override val connector: CassandraConnection) extends Database[OrderingContext](connector) with UnitOfWork {
  object OrderTable extends OrderByIdTable with connector.Connector
  object OrderByBuyerTable extends OrderByBuyerTable with connector.Connector
  object CardTypeTable extends CardTypeTable with connector.Connector
  object ClientRequestTable extends ClientRequestTable with connector.Connector
  object BuyerTable extends BuyerTable with connector.Connector

  override def saveChangeAsync(transactions: Transactions): Future[Int] = {
    transactions.execute()
  }

  override def saveEntitiesAsync(transactions: Transactions): Future[Boolean] = {
    for {
      result <- dispatchDomainEventsAsync()
      saveResult <- saveChangeAsync(transactions)
    } yield true
  }

  private def dispatchDomainEventsAsync(): Future[Unit] = {
    //mediator
    Future.unit
  }
}

object OrderingContext extends OrderingContext(Connector.connector)
