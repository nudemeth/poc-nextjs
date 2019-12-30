package nudemeth.poc.ordering.infrastructure.idempotency

import java.util.UUID

import scala.concurrent.{ ExecutionContext, Future }

trait RequestManagerOperations {
  def existAsync(id: UUID)(implicit executionContext: ExecutionContext): Future[Boolean]
  def createRequestForCommandAsync[T](id: UUID): Future[Unit]
}
