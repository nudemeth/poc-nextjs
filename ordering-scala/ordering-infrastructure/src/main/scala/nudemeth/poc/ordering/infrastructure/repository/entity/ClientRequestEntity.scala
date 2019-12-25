package nudemeth.poc.ordering.infrastructure.repository.entity

import java.time.OffsetDateTime
import java.util.UUID

case class ClientRequestEntity(id: UUID, name: String, time: OffsetDateTime) {

}
