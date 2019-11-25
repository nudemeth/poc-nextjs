package nudemeth.poc.ordering.domain.model.aggregate.buyer

object CardType extends Enumeration {
  type CardType = Value

  val Amex: CardType = Value("Amex")
  val Visa: CardType = Value("Visa")
  val MasterCard: CardType = Value("MasterCard")

  def apply(x: String): CardType = {
    x match {
      case "Amex" => Amex
      case "Visa" => Visa
      case "MasterCard" => MasterCard
    }
  }
}
