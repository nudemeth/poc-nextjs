package nudemeth.poc.ordering.domain.exception

case class OrderingDomainException(message: String) extends Exception(message) {
  def this(message: String, cause: Throwable) {
    this(message)
    initCause(cause)
  }
}
