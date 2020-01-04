package nudemeth.poc.ordering.infrastructure

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.infrastructure.repository.table._

class OrderingContext(override val connector: CassandraConnection) extends Database[OrderingContext](connector) {
  object OrderTable extends OrderByIdTable with connector.Connector
  object OrderByBuyerTable extends OrderByBuyerTable with connector.Connector
  object CardTypeTable extends CardTypeTable with connector.Connector
  object ClientRequestTable extends ClientRequestTable with connector.Connector
}

object OrderingContext extends OrderingContext(Connector.connector)
