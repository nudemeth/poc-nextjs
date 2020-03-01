package nudemeth.poc.ordering.domain.model

import scala.concurrent.Future

trait Transactions[T] {
  def execute(): Future[T]
}
