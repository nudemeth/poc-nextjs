package nudemeth.poc.ordering.infrastructure.eventbus

import spray.json.JsObject

trait IntegrationEventJsonConverter {
  def readJson[T <: IntegrationEvent](value: JsObject): T
  def writeJson[T <: IntegrationEvent](value: T): JsObject
}
