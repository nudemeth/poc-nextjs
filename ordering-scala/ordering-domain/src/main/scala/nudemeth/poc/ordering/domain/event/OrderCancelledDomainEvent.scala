package nudemeth.poc.ordering.domain.event

import nudemeth.poc.ordering.domain.model.aggregate.order.Order
import nudemeth.poc.ordering.util.mediator.Notification

case class OrderCancelledDomainEvent(order: Order) extends Notification {

}
