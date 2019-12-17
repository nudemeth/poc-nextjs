package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.Future

class Mediator(handlers: Map[_ <: RequestHandler[_ <: Request[Any], Any], Class[_ <: Request[Any]]]) extends MediatorDuty {
  override def send[TResponse](request: Request[TResponse]): Future[TResponse] = {
    val handler = handlers.find(h => h._2 == request.getClass).getOrElse(throw new NoSuchElementException(classOf[Request[TResponse]].toString))
    handler.asInstanceOf[RequestHandler[Request[TResponse], TResponse]].handle(request, this)
  }
}
