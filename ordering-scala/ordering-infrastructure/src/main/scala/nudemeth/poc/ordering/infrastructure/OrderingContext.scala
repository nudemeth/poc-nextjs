package nudemeth.poc.ordering.infrastructure

import com.outworkers.phantom.dsl._
import nudemeth.poc.ordering.domain.model.{ Transactions, UnitOfWork }
import nudemeth.poc.ordering.infrastructure.repository.OrderingDatabase
import nudemeth.poc.ordering.util.mediator.MediatorDuty

import scala.concurrent.Future

case class OrderingContext(database: OrderingDatabase, mediator: MediatorDuty) extends UnitOfWork {
  val OrderTable: database.OrderTable.type = database.OrderTable
  val OrderByBuyerTable: database.OrderByBuyerTable.type = database.OrderByBuyerTable
  val CardTypeTable: database.CardTypeTable.type = database.CardTypeTable
  val ClientRequestTable: database.ClientRequestTable.type = database.ClientRequestTable
  val BuyerTable: database.BuyerTable.type = database.BuyerTable

  override def saveChangesAsync[T](transactions: Transactions[T]): Future[T] = {
    transactions.execute()
  }

  override def saveEntitiesAsync[T](transactions: Transactions[T]): Future[Boolean] = {
    for {
      result <- dispatchDomainEventsAsync()
      saveResult <- saveChangesAsync(transactions)
    } yield true
  }

  private def dispatchDomainEventsAsync(): Future[Unit] = {
    //mediator
    Future.unit
  }
}