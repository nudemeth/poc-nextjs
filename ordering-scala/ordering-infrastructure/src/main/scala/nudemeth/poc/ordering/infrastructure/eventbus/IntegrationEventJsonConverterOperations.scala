package nudemeth.poc.ordering.infrastructure.eventbus

import java.time.{ Instant, OffsetDateTime, ZoneOffset }
import java.time.format.DateTimeFormatter
import java.util.UUID

import spray.json._

trait IntegrationEventJsonConverterOperations {
  implicit object UUIDFormat extends JsonFormat[UUID] {
    def write(uuid: UUID): JsString = JsString(uuid.toString)
    def read(value: JsValue): UUID = {
      value match {
        case JsString(uuid) => UUID.fromString(uuid)
        case _ => throw DeserializationException("Expected hexadecimal UUID string")
      }
    }
  }

  implicit object InstantFormat extends JsonFormat[Instant] {
    private val parser: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    def write(timestamp: Instant): JsString = JsString(timestamp.atOffset(ZoneOffset.UTC).format(parser))
    def read(value: JsValue): Instant = {
      value match {
        case JsString(timestamp) => OffsetDateTime.from(parser.parse(timestamp)).toInstant
        case _ => throw DeserializationException("Expected ISO datetime format string")
      }
    }
  }

  def readJson[T <: IntegrationEvent](value: JsValue): T
  def writeJson[T <: IntegrationEvent](value: T): JsValue
}
