package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future

trait OrderQueryable {
  def getOrderAsync(id: UUID): Future[Option[Order]]
  def getOrdersByUserIdAsync(userId: UUID): Future[Vector[OrderSummary]]
  def getCardTypesAsync: Future[Vector[CardType]]
  def deleteOrderAsync(id: UUID, userId: UUID): Future[Boolean]
}
