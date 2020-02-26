package nudemeth.poc.ordering.infrastructure.repository

import com.outworkers.phantom.dsl.Database
import nudemeth.poc.ordering.infrastructure.{ Connector, OrderingContext }
import nudemeth.poc.ordering.infrastructure.idempotency.table.ClientRequestTable
import nudemeth.poc.ordering.infrastructure.repository.table.{ BuyerTable, CardTypeTable, OrderByBuyerTable, OrderByIdTable }

case class OrderingDatabase() extends Database[OrderingContext](Connector.connector) {
  object OrderTable extends OrderByIdTable with connector.Connector
  object OrderByBuyerTable extends OrderByBuyerTable with connector.Connector
  object CardTypeTable extends CardTypeTable with connector.Connector
  object ClientRequestTable extends ClientRequestTable with connector.Connector
  object BuyerTable extends BuyerTable with connector.Connector
}
