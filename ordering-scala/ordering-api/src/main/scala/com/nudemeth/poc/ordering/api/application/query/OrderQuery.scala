package com.nudemeth.poc.ordering.api.application.query
import java.time.Instant
import java.util.UUID

import com.nudemeth.poc.ordering.domain.model.order.{ Address, Order }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OrderQuery(connStr: String) extends OrderQueryable {
  override def GetOrderAsync(id: UUID): Future[Option[Order]] = {
    for {
      e <- Database.OrderModel.getByUserId(id)
      d <- mapToDomain(e)
    } yield d
  }

  override def GetOrdersFromUserAsync(id: UUID): Future[List[Order]] = {
    Future.successful(List(Order("", "", None, 0, "", "", "", Some(Instant.now))))
  }

  override def GetCardTypesAsync(): Future[Order] = {
    Future.successful(Order("", "", None, 0, "", "", "", Some(Instant.now)))
  }

  private def mapToDomain(entity: Option[OrderEntity]): Future[Option[Order]] = Future {
    entity.map(e => {
      Order(
        e.userId,
        e.userName,
        Some(Address(e.street, e.city, e.state, e.country, e.zipCode)),
        e.cardTypeId,
        e.cardNumber,
        e.cardSecurityNumber,
        e.cardHolderName,
        e.cardExpiration.map(c => c.toInstant),
        e.buyerId,
        e.paymentMethodId)
    })
  }
}
