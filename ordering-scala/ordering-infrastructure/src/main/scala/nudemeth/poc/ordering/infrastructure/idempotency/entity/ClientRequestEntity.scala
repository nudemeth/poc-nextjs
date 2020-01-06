package nudemeth.poc.ordering.infrastructure.idempotency.entity

import java.time.OffsetDateTime
import java.util.UUID

case class ClientRequestEntity(id: UUID, name: String, time: OffsetDateTime) {

}
