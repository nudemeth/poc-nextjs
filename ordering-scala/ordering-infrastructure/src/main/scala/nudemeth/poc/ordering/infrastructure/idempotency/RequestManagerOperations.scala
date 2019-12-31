package nudemeth.poc.ordering.infrastructure.idempotency

import java.util.UUID

import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.runtime.universe._

trait RequestManagerOperations {
  def existAsync(id: UUID)(implicit executor: ExecutionContext): Future[Boolean]
  def createRequestForCommandAsync[T](id: UUID)(implicit executor: ExecutionContext, tag: TypeTag[T]): Future[Unit]
}
