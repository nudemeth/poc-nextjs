package nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.api.application.query.entity.{ OrderByUserEntity, OrderEntity }
import nudemeth.poc.ordering.api.application.query.model.{ CardTypeModel, OrderByUserModel, OrderModel }

import scala.concurrent.Future

class OrderDatabase(override val connector: CassandraConnection) extends Database[OrderDatabase](connector) {
  object OrderModel extends OrderModel with connector.Connector
  object OrderByUserModel extends OrderByUserModel with connector.Connector
  object CardTypeModel extends CardTypeModel with connector.Connector

  def saveOrUpdate(order: OrderEntity): Future[ResultSet] = {
    val orderByUser = OrderByUserEntity(order.orderId, order.buyerId, order.orderDate, order.statusName, order.orderItems.size)
    Batch.logged
      .add(OrderModel.saveOrUpdateTransaction(order))
      .add(OrderByUserModel.saveOrUpdateTransaction(orderByUser))
      .future()
  }

  def delete(order: OrderEntity): Future[ResultSet] = {
    Batch.logged
      .add(OrderModel.deleteByIdTransaction(order.orderId))
      .add(OrderByUserModel.deleteByUserIdAndIdTransaction(order.buyerId, order.orderId))
      .future()
  }
}

object OrderDatabase extends OrderDatabase(Connector.connector)