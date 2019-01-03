package nudemeth.poc.ordering.domain.model.aggregate.order

case class OrderItem(
  productId: Int,
  productName: String,
  unitPrice: BigDecimal,
  discount: BigDecimal,
  pictureUrl: String,
  units: Int = 1) {

}
