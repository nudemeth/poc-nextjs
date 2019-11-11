package nudemeth.poc.ordering.api.infrastructure.service

import java.util.UUID

import akka.actor.{ Actor, ActorLogging, Props }
import pdi.jwt.{ Jwt, JwtOptions }

import scala.util.{ Failure, Success, Try }
import spray.json._

object IdentityService {
  final case class ExtractUserIdentity(token: String)
  final case class UserIdentity(id: UUID, login: String)

  def props: Props = Props(new IdentityService)
}

class IdentityService extends Actor with ActorLogging {
  import nudemeth.poc.ordering.api.infrastructure.service.IdentityService._
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

  implicit val userIdentityFormat: RootJsonFormat[UserIdentity] = jsonFormat2(UserIdentity)

  private def tryExtractUserIdentity(token: String): Try[UserIdentity] = {
    for {
      decodedJwt <- Jwt.decode(token, JwtOptions(signature = false))
      userIdentity <- Try(decodedJwt.content.parseJson.convertTo[UserIdentity])
    } yield userIdentity
  }

  private def responseUserIdentity(token: String): Unit = {
    tryExtractUserIdentity(token) match {
      case Success(userIdentity) => sender() ! Some(userIdentity)
      case Failure(ex) =>
        log.warning(s"Invalid token: token=$token, error=${ex.getMessage}")
        sender() ! None
    }
  }

  override def receive: Receive = {
    case ExtractUserIdentity(token) => responseUserIdentity(token)
  }
}
