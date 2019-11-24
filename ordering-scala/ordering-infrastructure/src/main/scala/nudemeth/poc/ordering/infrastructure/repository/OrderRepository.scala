package nudemeth.poc.ordering.infrastructure.repository
import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.order.{ Address, Order, OrderItem }
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

  private def mapToDomainModel(mbEntity: Option[OrderEntity]): Option[Order] = {
    mbEntity.map { e =>
      Order(
        e.orderId,
        Some(e.buyerId),
        e.orderDate.toInstant,
        Some(Address(e.addressStreet, e.addressCity, e.addressState, e.addressCountry, e.addressZipCode)),
        e.statusName,
        e.orderItems.map { i =>
          OrderItem(
            i._1,
            i._2._1,
            i._2._2,
            i._2._3,
            i._2._4,
            i._2._5)
        }.toVector,
        e.paymentMethodId,
        Some(e.description))
    }
  }
}
