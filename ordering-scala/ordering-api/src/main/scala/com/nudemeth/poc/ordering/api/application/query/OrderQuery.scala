package com.nudemeth.poc.ordering.api.application.query
import java.time.Instant
import java.util.UUID

import com.nudemeth.poc.ordering.domain.model.order.Order

import scala.concurrent.Future

class OrderQuery(connStr: String) extends OrderQueryable {
  override def GetOrderAsync(id: UUID): Future[Order] = {
    Future.successful(Order("", "", None, 0, "", "", "", Instant.now))
  }

  override def GetOrdersFromUserAsync(id: UUID): Future[Order] = {
    Future.successful(Order("", "", None, 0, "", "", "", Instant.now))
  }

  override def GetCardTypesAsync(): Future[Order] = {
    Future.successful(Order("", "", None, 0, "", "", "", Instant.now))
  }
}
