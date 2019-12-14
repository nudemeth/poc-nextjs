package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.Future

trait RequestHandler[TRequest <: Request[TResponse], TResponse] {
  def handle(request: TRequest): Future[TResponse]
}
