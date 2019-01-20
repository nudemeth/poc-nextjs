package nudemeth.poc.ordering.api.controller

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import nudemeth.poc.ordering.api.application.query.viewmodel.Order
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

  implicit object OrderListFormat extends RootJsonFormat[List[Order]] {
    private val parser: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    def write(orders: List[Order]) = JsArray(orders.map(o => o.toJson).toVector)
    def read(value: JsValue): List[Order] = {
      value match {
        case JsArray(Vector(JsNumber(orderNumber), JsString(date), JsString(status), JsString(description), JsString(street), JsString(city), JsString(country), JsString(zipCode), JsNumber(total))) =>
          List(Order(orderNumber.toInt, ZonedDateTime.from(parser.parse(date)), status, description, street, city, country, zipCode, total.toDouble))
        case _ => throw DeserializationException("Expected array of Order")
      }
    }
  }

  implicit val orderJsonFormat: RootJsonFormat[Order] = jsonFormat9(Order)
  implicit val ordersJsonFormat: RootJsonFormat[List[Order]] = OrderListFormat

  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed] = jsonFormat1(ActionPerformed)
}
