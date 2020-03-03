package nudemeth.poc.ordering.infrastructure.repository
import java.time.ZoneOffset
import java.util.UUID

import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.{ Transactions, UnitOfWork }
import nudemeth.poc.ordering.domain.model.aggregate.order
import nudemeth.poc.ordering.domain.model.aggregate.buyer.{ CardType, PaymentMethod }
import nudemeth.poc.ordering.domain.model.aggregate.order.{ Address, Order, OrderItem, OrderPayment, OrderPaymentRepositoryOperations }
import nudemeth.poc.ordering.infrastructure.{ Connector, OrderingContext }
import nudemeth.poc.ordering.infrastructure.repository.entity.{ OrderByBuyerEntity, OrderByIdEntity }
import nudemeth.poc.ordering.util.mediator.{ Mediator, Notification }

import scala.concurrent.Future

case class OrderRepository(orderingContext: OrderingContext) extends OrderPaymentRepositoryOperations {
  implicit val session: Session = Connector.connector.session

  override val unitOfWork: UnitOfWork = orderingContext

  override def getOrderAsync(id: UUID): Future[Option[OrderPayment]] = {
    orderingContext.OrderTable.getById(id).map { e =>
      mapToDomainModel(e)
    }
  }

  override def addOrUpdateOrderAsync(orderPayment: OrderPayment): Transactions[Unit] = {
    val orderByIdEntity = OrderByIdEntity(
      orderPayment.order.orderId,
      orderPayment.order.buyerId,
      orderPayment.order.orderDate.atOffset(ZoneOffset.UTC),
      orderPayment.order.description,
      orderPayment.order.address.city,
      orderPayment.order.address.country,
      orderPayment.order.address.state,
      orderPayment.order.address.street,
      orderPayment.order.address.zipCode,
      orderPayment.order.orderStatus,
      orderPayment.paymentMethod.alias,
      orderPayment.paymentMethod.cardNumber,
      orderPayment.paymentMethod.cardSecurityNumber,
      orderPayment.paymentMethod.cardHolderName,
      orderPayment.paymentMethod.cardExpiration.atOffset(ZoneOffset.UTC),
      orderPayment.paymentMethod.cardType.toString,
      orderPayment.order.orderItems.map(o => o.productId -> (o.productName, o.pictureUrl, o.unitPrice, o.discount, o.units)).toMap)
    val orderByBuyerEntity = OrderByBuyerEntity(orderPayment.order.orderId, orderPayment.order.buyerId, orderPayment.order.orderDate.atOffset(ZoneOffset.UTC), orderPayment.order.orderStatus, orderPayment.order.orderItems.size)
    val batch = Batch.logged
      .add(orderingContext.OrderTable.saveOrUpdateTransaction(orderByIdEntity))
      .add(orderingContext.OrderByBuyerTable.saveOrUpdateTransaction(orderByBuyerEntity))
    CassandraTransactions(batch, _ => (), orderPayment.domainEvents)
  }

  private def mapToDomainModel(mbEntity: Option[OrderByIdEntity]): Option[OrderPayment] = {
    mbEntity.map { e =>
      order.OrderPayment(
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
          CardType(e.paymentMethodCardNumber)), Vector())
    }
  }
}
