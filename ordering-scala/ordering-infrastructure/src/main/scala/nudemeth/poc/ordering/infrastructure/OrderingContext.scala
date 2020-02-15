package nudemeth.poc.ordering.infrastructure

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.UnitOfWork
import nudemeth.poc.ordering.infrastructure.idempotency.table._
import nudemeth.poc.ordering.infrastructure.repository.table._

import scala.concurrent.Future

class OrderingContext(override val connector: CassandraConnection) extends Database[OrderingContext](connector) with UnitOfWork {
  object OrderTable extends OrderByIdTable with connector.Connector
  object OrderByBuyerTable extends OrderByBuyerTable with connector.Connector
  object CardTypeTable extends CardTypeTable with connector.Connector
  object ClientRequestTable extends ClientRequestTable with connector.Connector
  object BuyerTable extends BuyerTable with connector.Connector

  override def saveChangeAsync(): Future[Int] = {
    Future.successful(0)
  }

  override def saveEntitiesAsync(): Future[Boolean] = {
    Future.successful(true)
  }
}

object OrderingContext extends OrderingContext(Connector.connector)
