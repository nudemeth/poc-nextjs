package nudemeth.poc.ordering.api.application.query.viewmodel

case class OrderItem(
  productName: String,
  units: Int,
  unitPrice: BigDecimal,
  pictureUrl: String) {

}
