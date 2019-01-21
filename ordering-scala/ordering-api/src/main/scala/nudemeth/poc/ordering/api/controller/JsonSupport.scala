package nudemeth.poc.ordering.api.controller

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import nudemeth.poc.ordering.api.application.query.viewmodel.{ Order, OrderItem }
import nudemeth.poc.ordering.api.controller.OrderingRegistryActor.ActionPerformed
import spray.json._

trait JsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit object UUIDFormat extends JsonFormat[UUID] {
    def write(uuid: UUID) = JsString(uuid.toString)
    def read(value: JsValue): UUID = {
      value match {
        case JsString(uuid) => UUID.fromString(uuid)
        case _ => throw DeserializationException("Expected hexadecimal UUID string")
      }
    }
  }

  implicit object ZonedDateTimeFormat extends JsonFormat[ZonedDateTime] {
    private val parser: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    def write(timestamp: ZonedDateTime) = JsString(timestamp.format(parser))
    def read(value: JsValue): ZonedDateTime = {
      value match {
        case JsString(timestamp) => ZonedDateTime.from(parser.parse(timestamp))
        case _ => throw DeserializationException("Expected ISO datetime format string")
      }
    }
  }

  /// To support array in root node
  implicit object OrderListFormat extends RootJsonFormat[Vector[Order]] {
    def write(orders: Vector[Order]) = JsArray(orders.map(o => o.toJson))
    def read(value: JsValue): Vector[Order] = {
      value match {
        case JsArray(arr) => arr.map(_.convertTo[Order])
        case _ => throw DeserializationException("Expected array of Order")
      }
    }
  }

  implicit val orderItemJsonFormat: RootJsonFormat[OrderItem] = jsonFormat4(OrderItem)
  implicit val orderJsonFormat: RootJsonFormat[Order] = jsonFormat10(Order)

  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed] = jsonFormat1(ActionPerformed)
}
