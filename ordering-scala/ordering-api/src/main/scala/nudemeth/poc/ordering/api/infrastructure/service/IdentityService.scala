package nudemeth.poc.ordering.api.infrastructure.service

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }

object IdentityService {
  final case class RequestUUID(header: String)

  def props: Props = Props(new IdentityService)
}

class IdentityService extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.infrastructure.service.IdentityService._

  override def receive: Receive = {
    case RequestUUID(header) => sender() ! UUID.fromString("2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df")
  }
}
