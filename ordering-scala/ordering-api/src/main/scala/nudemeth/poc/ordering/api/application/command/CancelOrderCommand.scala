package nudemeth.poc.ordering.api.application.command

import java.util.UUID

import nudemeth.poc.ordering.api.infrastructure.mediator.Request

case class CancelOrderCommand(orderId: UUID) extends Request[Boolean]
