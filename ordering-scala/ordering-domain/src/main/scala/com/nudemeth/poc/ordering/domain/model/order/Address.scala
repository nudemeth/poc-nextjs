package com.nudemeth.poc.ordering.domain.model.order

case class Address(
  street: String,
  city: String,
  state: Option[String],
  country: String,
  zipCode: String) {

}
