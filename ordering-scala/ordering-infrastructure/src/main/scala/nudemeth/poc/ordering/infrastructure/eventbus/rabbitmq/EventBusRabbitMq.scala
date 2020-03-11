package nudemeth.poc.ordering.infrastructure.eventbus.rabbitmq

import java.nio.charset.StandardCharsets
import java.time.{ Instant, OffsetDateTime, ZoneOffset }
import java.time.format.DateTimeFormatter
import java.util.UUID

import com.newmotion.akka.rabbitmq.{ BasicProperties, Channel, ChannelActor, Connection, CreateChannel }
import com.rabbitmq.client.AMQP.BasicProperties
import nudemeth.poc.ordering.infrastructure.eventbus._
import spray.json._
import DefaultJsonProtocol._

case class EventBusRabbitMq(connection: Connection, eventJsonConverter: IntegrationEventJsonConverter) extends EventBusOperations {
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

  implicit object IntegrationEventFormat extends RootJsonFormat[IntegrationEvent] {
    def write(event: IntegrationEvent): JsObject = eventJsonConverter.writeJson(event)
    def read(value: JsValue): IntegrationEvent = eventJsonConverter.readJson(value.asJsObject())
  }

  private val BROKER_NAME = "eshop_event_bus"

  private def publish(channel: Channel, eventName: String, body: Array[Byte]): Unit = {
    val props = new BasicProperties().builder().deliveryMode(2).build()
    channel.exchangeDeclare(BROKER_NAME, "direct")
    channel.basicPublish(BROKER_NAME, eventName, true, props, body)
  }

  override def publish(event: IntegrationEvent): Unit = {
    val channel = connection.createChannel()
    val body = event.toJson.compactPrint.getBytes(StandardCharsets.UTF_8)
    publish(channel, event.getClass.getName, body)
  }

  override def subscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }

  override def unsubscribe[T <: IntegrationEvent, TH <: IntegrationEventHandlerOperations[T]](): Unit = {

  }

}
