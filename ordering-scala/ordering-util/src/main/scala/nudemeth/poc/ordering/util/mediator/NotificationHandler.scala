package nudemeth.poc.ordering.util.mediator

import scala.concurrent.{ ExecutionContext, Future }

trait NotificationHandler[TNotification <: Notification] {
  def handle(notification: TNotification, mediator: MediatorDuty)(implicit executor: ExecutionContext): Future[Unit]
}
