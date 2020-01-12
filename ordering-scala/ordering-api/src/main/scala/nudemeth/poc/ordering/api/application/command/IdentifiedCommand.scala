package nudemeth.poc.ordering.api.application.command

import java.util.UUID

import nudemeth.poc.ordering.util.mediator.Request

case class IdentifiedCommand[TRequest <: Request[TResponse], TResponse](command: TRequest, id: UUID) extends Request[TResponse] {

}
