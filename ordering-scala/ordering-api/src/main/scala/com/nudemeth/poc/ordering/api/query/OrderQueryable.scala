package com.nudemeth.poc.ordering.api.query

import java.util.UUID

import com.nudemeth.poc.ordering.domain.model.order.Order

import scala.concurrent.Future

trait OrderQueryable {
  def GetOrderAsync(id: Int): Future[Order]
  def GetOrdersFromUserAsync(id: UUID): Future[Order]
  def GetCardTypesAsync(): Future[Order]
}
