package nudemeth.poc.ordering.api.infrastructure.mediator

import scala.concurrent.Future

trait MediatorDuty {
  def send[TResponse](request: Request[TResponse]): Future[TResponse]
}
