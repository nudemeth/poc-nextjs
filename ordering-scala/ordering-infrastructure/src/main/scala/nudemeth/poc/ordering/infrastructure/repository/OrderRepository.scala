package nudemeth.poc.ordering.infrastructure.repository
import java.time.ZoneOffset
import java.util.UUID

import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.aggregate.buyer.{ CardType, PaymentMethod }
import nudemeth.poc.ordering.domain.model.aggregate.order.{ Address, Order, OrderItem }
import nudemeth.poc.ordering.infrastructure.{ Connector, OrderingContext }
import nudemeth.poc.ordering.infrastructure.repository.entity.{ OrderByUserEntity, OrderEntity }

import scala.concurrent.Future

class OrderRepository extends OrderRepositoryOperations {
  implicit val session: Session = Connector.connector.session
  override def getOrderAsync(id: UUID): Future[Option[(Order, PaymentMethod)]] = {
    OrderingContext.OrderTable.getById(id).map { e =>
      mapToDomainModel(e)
    }
  }

  override def addOrUpdateOrderAsync(order: Order, paymentMethod: PaymentMethod): Future[Unit] = {
    val orderByIdEntity = OrderEntity(
      order.orderId,
      order.buyerId,
      order.orderDate.atOffset(ZoneOffset.UTC),
      order.description,
      order.address.city,
      order.address.country,
      order.address.state,
      order.address.street,
      order.address.zipCode,
      order.orderStatus,
      paymentMethod.alias,
      paymentMethod.cardNumber,
      paymentMethod.cardSecurityNumber,
      paymentMethod.cardHolderName,
      paymentMethod.cardExpiration.atOffset(ZoneOffset.UTC),
      paymentMethod.cardType.toString,
      order.orderItems.map { o =>
        o.productId -> (o.productName, o.pictureUrl, o.unitPrice, o.discount, o.units)
      }.toMap)
    val orderByBuyerEntity = OrderByUserEntity(order.orderId, order.buyerId, order.orderDate.atOffset(ZoneOffset.UTC), order.orderStatus, order.orderItems.size)
    Batch.logged
      .add(OrderingContext.OrderTable.saveOrUpdateTransaction(orderByIdEntity))
      .add(OrderingContext.OrderByBuyerTable.saveOrUpdateTransaction(orderByBuyerEntity))
      .future()
      .flatMap(_ => Future.unit)
  }

  private def mapToDomainModel(mbEntity: Option[OrderEntity]): Option[(Order, PaymentMethod)] = {
    mbEntity.map { e =>
      (
        Order(
          e.orderId,
          e.buyerId,
          e.orderDate.toInstant,
          Address(e.addressStreet, e.addressCity, e.addressState, e.addressCountry, e.addressZipCode),
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
          e.description),
          PaymentMethod(
            e.paymentMethodAlias,
            e.paymentMethodCardNumber,
            e.paymentMethodCardSecurityNumber,
            e.paymentMethodCardHolderName,
            e.paymentMethodCardExpiration.toInstant,
            CardType(e.paymentMethodCardNumber)))
    }
  }
}
