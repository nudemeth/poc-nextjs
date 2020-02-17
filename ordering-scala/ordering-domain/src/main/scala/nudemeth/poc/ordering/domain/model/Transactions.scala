package nudemeth.poc.ordering.domain.model

import scala.concurrent.Future

trait Transactions {
  def execute[T](): Future[T]
}
