package nudemeth.poc.ordering.infrastructure.repository

import nudemeth.poc.ordering.domain.model.Transactions

import scala.concurrent.Future

case class CassandraTransactions() extends Transactions {
  override def execute[T](): Future[T] = {
    Future.successful(new T)
  }
}
