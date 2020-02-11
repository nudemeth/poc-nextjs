package nudemeth.poc.ordering.util.mediator

import scala.concurrent.{ ExecutionContext, Future }

trait MediatorDuty {
  def send[TResponse](request: Request[TResponse])(implicit executor: ExecutionContext): Future[TResponse]
  def publish[TNotification <: Notification](notification: TNotification)(implicit executor: ExecutionContext): Future[Unit]
}
