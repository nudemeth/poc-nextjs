package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.{ ExecutionContext, Future }

trait RequestHandler[TRequest <: Request[TResponse], +TResponse] {
  def handle(command: TRequest, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[TResponse]
}
