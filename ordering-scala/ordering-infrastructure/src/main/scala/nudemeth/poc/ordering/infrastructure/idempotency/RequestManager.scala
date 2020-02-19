package nudemeth.poc.ordering.infrastructure.idempotency
import java.time.OffsetDateTime
import java.util.UUID

import nudemeth.poc.ordering.domain.exception.OrderingDomainException
import nudemeth.poc.ordering.infrastructure.OrderingContext
import nudemeth.poc.ordering.infrastructure.idempotency.entity.ClientRequestEntity

import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.runtime.universe._

case class RequestManager(orderingContext: OrderingContext) extends RequestManagerOperations {
  override def existAsync(id: UUID)(implicit executor: ExecutionContext): Future[Boolean] = {
    orderingContext.ClientRequestTable.getById(id).map(_.isDefined)
  }

  override def createRequestForCommandAsync[T](id: UUID)(implicit executor: ExecutionContext, tag: TypeTag[T]): Future[Unit] = {
    existAsync(id).flatMap { r =>
      if (r) {
        throw OrderingDomainException(s"Request with $id already exists")
      }
      val clientRequest = entity.ClientRequestEntity(id, typeOf[T].typeSymbol.fullName, OffsetDateTime.now())
      orderingContext.ClientRequestTable.saveOrUpdate(clientRequest).map(_ => ())
    }
  }
}
