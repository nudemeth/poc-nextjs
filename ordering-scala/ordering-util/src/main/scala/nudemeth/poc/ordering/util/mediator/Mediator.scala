package nudemeth.poc.ordering.util.mediator

import scala.concurrent.{ ExecutionContext, Future }

class Mediator(handlers: Map[Class[_ <: Request[Any]], _ <: RequestHandler[_ <: Request[Any], Any]], notificationHandlers: Map[Class[_ <: Notification], _ <: NotificationHandler[_ <: Notification]]) extends MediatorDuty {
  override def send[TResponse](request: Request[TResponse])(implicit executor: ExecutionContext): Future[TResponse] = {
    val handler = handlers.find(h => h._1.equals(request.getClass)).getOrElse(throw new NoSuchElementException(classOf[Request[TResponse]].toString))
    handler.asInstanceOf[RequestHandler[Request[TResponse], TResponse]].handle(request, this)
  }

  override def publish[TNotification <: Notification](notification: TNotification)(implicit executor: ExecutionContext): Future[Unit] = {
    val handler = notificationHandlers.find(h => h._1.equals(notification.getClass)).getOrElse(throw new NoSuchElementException(notification.getClass.toString))
    handler.asInstanceOf[NotificationHandler[Notification]].handle(notification, this)
  }
}
