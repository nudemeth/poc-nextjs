package nudemeth.poc.ordering.domain.model.aggregate.order

import java.util.UUID

case class OrderItem(
  productId: UUID,
  productName: String,
  pictureUrl: String,
  unitPrice: BigDecimal,
  discount: BigDecimal,
  units: Int) {

}
