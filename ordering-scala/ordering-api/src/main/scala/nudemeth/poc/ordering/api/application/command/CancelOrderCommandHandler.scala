package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.api.infrastructure.mediator.{ MediatorDuty, RequestHandler }

import scala.concurrent.Future

case class CancelOrderCommandHandler() extends RequestHandler[CancelOrderCommand, Boolean] {
  override def handle(request: CancelOrderCommand, mediator: MediatorDuty): Future[Boolean] = {
    Future.successful(true)
  }
}
