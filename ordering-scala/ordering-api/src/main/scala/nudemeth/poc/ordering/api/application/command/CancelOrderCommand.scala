package nudemeth.poc.ordering.api.application.command

import java.util.UUID

import nudemeth.poc.ordering.util.mediator.Request

case class CancelOrderCommand(orderId: UUID) extends Request[Boolean]
