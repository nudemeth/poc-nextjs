package nudemeth.poc.ordering.api.controller

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, ShipOrderCommand }
import nudemeth.poc.ordering.api.application.query.viewmodel.{ CardType, Order, OrderItem, OrderSummary }
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

  implicit object OffsetDateTimeFormat extends JsonFormat[OffsetDateTime] {
    private val parser: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    def write(timestamp: OffsetDateTime) = JsString(timestamp.format(parser))
    def read(value: JsValue): OffsetDateTime = {
      value match {
        case JsString(timestamp) => OffsetDateTime.from(parser.parse(timestamp))
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

  implicit object OrderSummaryListFormat extends RootJsonFormat[Vector[OrderSummary]] {
    def write(orders: Vector[OrderSummary]) = JsArray(orders.map(o => o.toJson))
    def read(value: JsValue): Vector[OrderSummary] = {
      value match {
        case JsArray(arr) => arr.map(_.convertTo[OrderSummary])
        case _ => throw DeserializationException("Expected array of OrderSummary")
      }
    }
  }

  implicit object CardTypeListFormat extends RootJsonFormat[Vector[CardType]] {
    def write(orders: Vector[CardType]) = JsArray(orders.map(o => o.toJson))
    def read(value: JsValue): Vector[CardType] = {
      value match {
        case JsArray(arr) => arr.map(_.convertTo[CardType])
        case _ => throw DeserializationException("Expected array of CardType")
      }
    }
  }

  implicit val orderItemJsonFormat: RootJsonFormat[OrderItem] = jsonFormat4(OrderItem)
  implicit val orderJsonFormat: RootJsonFormat[Order] = jsonFormat10(Order)
  implicit val orderSummaryJsonFormat: RootJsonFormat[OrderSummary] = jsonFormat4(OrderSummary)
  implicit val cardTypesJsonFormat: RootJsonFormat[CardType] = jsonFormat1(CardType)
  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed] = jsonFormat1(ActionPerformed)
  implicit val cancelOrderCommandJsonFormat: RootJsonFormat[CancelOrderCommand] = jsonFormat1(CancelOrderCommand)
  implicit val shipOrderCommandJsonFormat: RootJsonFormat[ShipOrderCommand] = jsonFormat1(ShipOrderCommand)
}
