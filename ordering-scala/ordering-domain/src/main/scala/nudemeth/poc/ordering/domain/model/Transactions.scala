package nudemeth.poc.ordering.domain.model

import nudemeth.poc.ordering.util.mediator.Notification

import scala.concurrent.Future

trait Transactions[T] {
  val domainEvents: Vector[Notification]
  def execute(): Future[T]
}
