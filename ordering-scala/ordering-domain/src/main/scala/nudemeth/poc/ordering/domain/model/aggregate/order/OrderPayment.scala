package nudemeth.poc.ordering.domain.model.aggregate.order

import nudemeth.poc.ordering.domain.model.{ AggregateRoot, Entity }
import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod

case class OrderPayment(order: Order, paymentMethod: PaymentMethod) extends Entity(order.orderId) with AggregateRoot
