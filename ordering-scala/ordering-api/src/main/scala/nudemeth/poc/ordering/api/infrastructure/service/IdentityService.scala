package nudemeth.poc.ordering.api.infrastructure.service

import java.util.UUID

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.{ ActorContext, Behaviors }
import pdi.jwt.{ Jwt, JwtOptions }

import scala.util.{ Failure, Success, Try }
import spray.json._

object IdentityService {
  import DefaultJsonProtocol._

  sealed trait Command
  final case class ExtractUserIdentity(token: String, replyTo: ActorRef[Option[UserIdentity]]) extends Command

  final case class UserIdentity(id: UUID, login: String)

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

  private def responseUserIdentity(token: String, replyTo: ActorRef[Option[UserIdentity]])(implicit ctx: ActorContext[Command]): Unit = {
    tryExtractUserIdentity(token) match {
      case Success(userIdentity) => replyTo ! Some(userIdentity)
      case Failure(ex) =>
        ctx.log.warn(s"Invalid token: token=$token, error=${ex.getMessage}")
        replyTo ! None
    }
  }

  def apply(): Behavior[Command] = Behaviors.receive { (ctx, message) =>
    message match {
      case ExtractUserIdentity(token, replyTo) =>
        responseUserIdentity(token, replyTo)(ctx)
        Behaviors.same
    }

  }
}
