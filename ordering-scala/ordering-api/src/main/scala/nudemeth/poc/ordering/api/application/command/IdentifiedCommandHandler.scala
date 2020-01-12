package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.util.mediator.{ MediatorDuty, Request, RequestHandler }

import scala.concurrent.{ ExecutionContext, Future }

case class IdentifiedCommandHandler[TRequest <: Request[TResponse], TResponse]() extends RequestHandler[IdentifiedCommand[TRequest, TResponse], TResponse] {
  override def handle(command: IdentifiedCommand[TRequest, TResponse], mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[TResponse] = {
    mediator.send(command.command)
  }
}
