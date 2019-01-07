package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
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

  /*implicit object OrderListFormat extends JsonFormat[List[Order]] {
    def write(orders: List[Order]) = JsArray(orders.map(o => o.toJson).toVector)
    def read(value: JsValue): List[Order] = {
      value match {
        case JsArray(Vector(JsString(id), JsString(name))) => List(Order(UUID.fromString(id), name))
        case _ => throw DeserializationException("Expected array of Order")
      }
    }
  }*/

  implicit val orderJsonFormat: RootJsonFormat[Order] = jsonFormat2(Order)
  implicit val ordersJsonFormat: RootJsonFormat[Orders] = jsonFormat1(Orders)

  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed] = jsonFormat1(ActionPerformed)
}
