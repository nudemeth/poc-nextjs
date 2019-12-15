package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.Future

class Mediator(handlers: Map[Class[_], RequestHandler[Request[_], _]]) extends MediatorDuty {
  override def send[TResponse](request: Request[TResponse]): Future[TResponse] = {
    val handler = handlers.getOrElse(request.getClass, throw new NoSuchElementException(classOf[Request[TResponse]].toString))
    handler.asInstanceOf[RequestHandler[Request[TResponse], TResponse]].handle(request, this)
  }
}
