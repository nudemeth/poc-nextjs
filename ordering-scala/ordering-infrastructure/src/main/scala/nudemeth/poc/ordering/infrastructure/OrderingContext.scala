package nudemeth.poc.ordering.infrastructure

import java.util.UUID

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.infrastructure.repository.entity.{ OrderByUserEntity, OrderEntity }
import nudemeth.poc.ordering.infrastructure.repository.model._

import scala.concurrent.Future

class OrderingContext(override val connector: CassandraConnection) extends Database[OrderingContext](connector) {
  object OrderModel extends OrderModel with connector.Connector
  object OrderByUserModel extends OrderByUserModel with connector.Connector
  object CardTypeModel extends CardTypeModel with connector.Connector
  object ClientRequestModel extends ClientRequestModel with connector.Connector

  def saveOrUpdate(order: OrderEntity, buyerId: UUID): Future[ResultSet] = {
    val orderByUser = OrderByUserEntity(order.orderId, buyerId, order.orderDate, order.statusName, order.orderItems.size)
    Batch.logged
      .add(OrderModel.saveOrUpdateTransaction(order))
      .add(OrderByUserModel.saveOrUpdateTransaction(orderByUser))
      .future()
  }

  def delete(orderId: UUID, buyerId: UUID): Future[ResultSet] = {
    Batch.logged
      .add(OrderModel.deleteByIdTransaction(orderId))
      .add(OrderByUserModel.deleteByUserIdAndIdTransaction(buyerId, orderId))
      .future()
  }
}

object OrderingContext extends OrderingContext(Connector.connector)
