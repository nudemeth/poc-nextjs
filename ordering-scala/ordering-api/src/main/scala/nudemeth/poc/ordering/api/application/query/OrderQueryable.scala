package nudemeth.poc.ordering.api.application.query

import java.util.UUID

import nudemeth.poc.ordering.domain.model.aggregate.buyer.CardType.CardType
import nudemeth.poc.ordering.domain.model.aggregate.order.Order

import scala.concurrent.Future

trait OrderQueryable {
  def GetOrderAsync(id: UUID): Future[Option[Order]]
  def GetOrdersFromUserAsync(userName: String): Future[List[Order]]
  def GetCardTypesAsync(): Future[List[CardType]]
}
