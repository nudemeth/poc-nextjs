package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.api.infrastructure.mediator.{ Request, RequestHandler, MediatorDuty }

import scala.concurrent.Future

case class IdentifiedCommandHandler[TRequest <: Request[TResponse], TResponse]() extends RequestHandler[IdentifiedCommand[TRequest, TResponse], TResponse] {
  override def handle(request: IdentifiedCommand[TRequest, TResponse], mediator: MediatorDuty): Future[TResponse] = {
    mediator.send(request.command)
  }
}
