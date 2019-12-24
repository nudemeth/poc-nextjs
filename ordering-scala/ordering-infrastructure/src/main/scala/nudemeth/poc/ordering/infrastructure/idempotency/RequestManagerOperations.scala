package nudemeth.poc.ordering.infrastructure.idempotency

import java.util.UUID

import scala.concurrent.Future

trait RequestManagerOperations {
  def existAsync(id: UUID): Future[Boolean]
  def createRequestForCommandAsync[T](id: UUID): Future[Unit]
}
