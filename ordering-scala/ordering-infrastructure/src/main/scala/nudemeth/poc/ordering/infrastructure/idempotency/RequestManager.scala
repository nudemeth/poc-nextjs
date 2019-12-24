package nudemeth.poc.ordering.infrastructure.idempotency
import java.util.UUID

import scala.concurrent.Future

case class RequestManager() extends RequestManagerOperations {
  override def existAsync(id: UUID): Future[Boolean] = {
    Future.successful(true)
  }

  override def createRequestForCommandAsync[T](id: UUID): Future[Unit] = {
    Future.unit
  }
}
