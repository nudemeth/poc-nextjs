package nudemeth.poc.ordering.api.infrastructure.service

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }

object IdentityService {
  final case class RequestUUID(header: String)
  final case class ResponseUUID(UUID: UUID)

  def props: Props = Props(new IdentityService)
}

class IdentityService extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.infrastructure.service.IdentityService._

  override def receive: Receive = {
    case RequestUUID(header) => sender() ! ResponseUUID(UUID.fromString("6bc6cfae-b04e-4b53-ba23-1a1b7260b121"))
  }
}
