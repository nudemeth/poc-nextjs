package nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

class OrderDatabase(override val connector: CassandraConnection) extends Database[OrderDatabase](connector) {
  object OrderModel extends OrderModel with connector.Connector
  object OrderByUserModel extends OrderByUserModel with connector.Connector
  object CardTypeModel extends CardTypeModel with connector.Connector

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = {
    Batch.logged
      .add(OrderModel.saveOrUpdateTransaction(order))
      .add(OrderByUserModel.saveOrUpdateTransaction(order))
      .future()
  }

  def delete(order: OrderEntity): Future[ResultSet] = {
    Batch.logged
      .add(OrderModel.deleteByIdTransaction(order.id))
      .add(OrderByUserModel.deleteByUserIdAndIdTransaction(order.userId, order.id))
      .future()
  }
}

object OrderDatabase extends OrderDatabase(Connector.connector)