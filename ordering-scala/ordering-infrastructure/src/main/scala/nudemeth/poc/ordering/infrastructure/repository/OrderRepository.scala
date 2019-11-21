package nudemeth.poc.ordering.infrastructure.repository
import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.order.{Address, Order, OrderItem}
import nudemeth.poc.ordering.infrastructure.repository.entity.OrderEntity

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderRepository extends OrderRepositoryOperations {
  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    OrderDatabase.OrderModel.getById(id).map { e =>
      mapToDomainModel(e)
    }
  }

  override def addOrderAsync(order: Order): Future[Order] = {
    Future.successful(order)
  }

  override def updateOrderAsync(order: Order): Future[Unit] = {
    Future.successful()
  }

  private def mapToDomainModel(mbEntity: Option[OrderEntity]): Option[Order] =  {
    mbEntity.map { e =>
      Order(
        e.orderId,
        None,
        None,
        Some(Address(e.addressStreet, e.addressCity, e.addressState, e.addressCountry, e.addressZipCode)),
        None,
        None,
        None,
        None,
        None,
        None,
        None
      )
    }
  }
}
