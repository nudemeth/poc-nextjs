package nudemeth.poc.ordering.api.infrastructure.service

import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}

object IdentityService {
  def props: Props = Props(new IdentityService)

  final case class GetUUID(header: String)
  final case class ReturnUUID(UUID: UUID)
}

class IdentityService extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.infrastructure.service.IdentityService._

  override def receive: Receive = {
    case GetUUID(header) => sender() ! ReturnUUID(UUID.fromString("6bc6cfae-b04e-4b53-ba23-1a1b7260b121"))
  }
}
