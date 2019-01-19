package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.api.application.query.viewmodel._

import scala.concurrent.Future

trait OrderQueryable {
  def getOrderAsync(id: UUID): Future[Option[Order]]
  def getOrdersByUserAsync(userName: String): Future[List[Order]]
  def getCardTypesAsync: Future[List[CardType]]
}
