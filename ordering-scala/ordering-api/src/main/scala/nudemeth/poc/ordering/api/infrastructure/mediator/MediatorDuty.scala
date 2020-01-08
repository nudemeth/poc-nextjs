package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.{ ExecutionContext, Future }

trait MediatorDuty {
  def send[TResponse](request: Request[TResponse])(implicit executor: ExecutionContext): Future[TResponse]
}
