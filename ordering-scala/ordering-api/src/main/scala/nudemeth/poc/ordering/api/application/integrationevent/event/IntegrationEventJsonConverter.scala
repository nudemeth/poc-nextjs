package nudemeth.poc.ordering.api.application.integrationevent.event

import nudemeth.poc.ordering.infrastructure.eventbus.{ IntegrationEvent, IntegrationEventJsonConverterOperations }
import spray.json._

object IntegrationEventJsonConverter extends IntegrationEventJsonConverterOperations with DefaultJsonProtocol {
  implicit val orderStatusChangedToCancelledIntegrationEventFormat: RootJsonFormat[OrderStatusChangedToCancelledIntegrationEvent] = jsonFormat5(OrderStatusChangedToCancelledIntegrationEvent)

  override def writeJson[T <: IntegrationEvent](value: T): JsValue = {
    value match {
      case c: OrderStatusChangedToCancelledIntegrationEvent => c.asInstanceOf[OrderStatusChangedToCancelledIntegrationEvent].toJson
    }
  }

  override def readJson[T <: IntegrationEvent](value: JsValue): T = {
    value.asJsObject match {
      case o: JsObject if Set("orderId", "orderStatus", "buyerName").forall(p => o.fields.contains(p)) =>
        orderStatusChangedToCancelledIntegrationEventFormat.read(value).asInstanceOf[T]
      case _ => throw DeserializationException("Expected subclass-object of IntegrationEvent")
    }
  }
}
