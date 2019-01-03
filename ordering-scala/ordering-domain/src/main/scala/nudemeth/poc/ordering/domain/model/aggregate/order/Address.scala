package nudemeth.poc.ordering.domain.model.aggregate.order

case class Address(
  street: String,
  city: String,
  state: Option[String],
  country: String,
  zipCode: String) {

}
