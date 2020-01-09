package nudemeth.poc.ordering.domain.model.aggregate

import nudemeth.poc.ordering.domain.model.aggregate.buyer.PaymentMethod
import nudemeth.poc.ordering.domain.model.aggregate.order.Order

case class OrderPayment(order: Order, paymentMethod: PaymentMethod)
