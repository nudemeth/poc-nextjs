package nudemeth.poc.ordering.infrastructure.eventbus

import java.time.Instant
import java.util.UUID

case class IntegrationEvent(id: UUID, creationDate: Instant)
