package nudemeth.poc.ordering.infrastructure.idempotency
import java.util.UUID

import nudemeth.poc.ordering.infrastructure.OrderingContext
import nudemeth.poc.ordering.infrastructure.repository.OrderDatabase

import scala.concurrent.{ ExecutionContext, Future }

case class RequestManager(context: OrderingContext) extends RequestManagerOperations {
  override def existAsync(id: UUID)(implicit executionContext: ExecutionContext): Future[Boolean] = {
    OrderDatabase.ClientRequestModel.getById(id).map(_.isDefined)
  }

  override def createRequestForCommandAsync[T](id: UUID): Future[Unit] = {
    Future.unit
  }
}
