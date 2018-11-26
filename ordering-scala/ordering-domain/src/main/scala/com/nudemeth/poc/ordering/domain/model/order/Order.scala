package com.nudemeth.poc.ordering.domain.model.order

import java.time.Instant

case class Order(userId: String,
                 userName: String,
                 address: Option[Address],
                 cardTypeId: Int,
                 cardNumber: String,
                 cardSecurityNumber: String,
                 cardHolderName: String,
                 cardExpiration: Instant,
                 buyerId: Option[Int] = None,
                 paymentMethodId: Option[Int] = None) {

}
