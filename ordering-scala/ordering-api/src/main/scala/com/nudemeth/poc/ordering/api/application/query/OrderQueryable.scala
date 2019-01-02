package com.nudemeth.poc.ordering.api.application.query

import java.util.UUID

import com.nudemeth.poc.ordering.domain.model.order.Order

import scala.concurrent.Future

trait OrderQueryable {
  def GetOrderAsync(id: UUID): Future[Option[Order]]
  def GetOrdersFromUserAsync(userName: String): Future[List[Order]]
  def GetCardTypesAsync(): Future[Order]
}
