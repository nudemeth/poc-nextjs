package nudemeth.poc.ordering.infrastructure.repository
import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.order.{ Order, OrderItem }
import nudemeth.poc.ordering.infrastructure.repository.entity.OrderEntity

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderRepository extends OrderRepositoryOperations {
  override def getOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- OrderDatabase.OrderModel.getById(id)
      m <- mapToViewModel(e)
    } yield m
  }

  override def addOrderAsync(order: Order): Future[Order] = {
    Future.successful(order)
  }

  override def updateOrderAsync(order: Order): Future[Unit] = {
    Future.successful()
  }

  private def mapToViewModel(mbEntity: Option[OrderEntity]): Future[Option[Order]] = Future {
    None
  }
}
