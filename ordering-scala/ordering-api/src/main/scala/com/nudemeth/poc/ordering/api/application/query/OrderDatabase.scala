package com.nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

class OrderDatabase(override val connector: CassandraConnection) extends Database[OrderDatabase](connector) {
  object OrderModel extends OrderModel with connector.Connector
}

object Database extends OrderDatabase(Connector.connector)