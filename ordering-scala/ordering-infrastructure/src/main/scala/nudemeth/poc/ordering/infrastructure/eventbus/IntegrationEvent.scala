package nudemeth.poc.ordering.infrastructure.eventbus

import java.time.Instant
import java.util.UUID
import scala.reflect.runtime.universe._

trait IntegrationEventProperties {
  val id: UUID = UUID.randomUUID()
  val creationDate: Instant = Instant.now()
}

class IntegrationEvent[T <: IntegrationEventProperties]()(implicit tag: TypeTag[T]) {
  this: IntegrationEventProperties =>
}
