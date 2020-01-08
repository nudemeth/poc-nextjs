package nudemeth.poc.ordering.api.application.command

import nudemeth.poc.ordering.api.infrastructure.mediator.{ MediatorDuty, RequestHandler }

import scala.concurrent.{ ExecutionContext, Future }

case class ShipOrderCommandHandler() extends RequestHandler[ShipOrderCommand, Boolean] {
  override def handle(request: ShipOrderCommand, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Boolean] = {
    Future.successful(true)
  }
}
