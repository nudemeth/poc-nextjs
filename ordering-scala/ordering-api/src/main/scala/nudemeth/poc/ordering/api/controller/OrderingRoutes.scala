package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives.{ get, post }
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.{ Route, ValidationRejection }
import akka.http.scaladsl.server.directives.Credentials
import akka.util.Timeout
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, ShipOrderCommand }
import nudemeth.poc.ordering.api.application.query.viewmodel.{ CardType, Order, OrderSummary }
import nudemeth.poc.ordering.api.controller.OrderingRegistryActor._
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService.{ ExtractUserIdentity, UserIdentity }

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.duration._
import scala.util.{ Failure, Success, Try }

class OrderingRoutes(orderingRegistryActor: ActorRef[OrderingRegistryActor.Command], identityRegistryActor: ActorRef[IdentityService.Command])(implicit system: ActorSystem[_]) extends JsonSupport {

  // Required by the `ask` (?) method below
  implicit lazy val timeout: Timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  lazy val orderingRoutes: Route =
    pathPrefix("api" / "v1" / "orders") {
      authenticateOAuth2Async(realm = "api", tokenAuthenticator) { userIdentity =>
        concat(
          getOrdersRoute(userIdentity),
          getOrderRoute,
          getCardTypesRoute,
          cancelOrderRoute,
          shipOrderRoute)
      }
    }

  def tokenAuthenticator(credentials: Credentials): Future[Option[UserIdentity]] = {
    credentials match {
      case Credentials.Provided(token) => identityRegistryActor ? (ExtractUserIdentity(token, _))
      case _ => Future.successful(None)
    }
  }

  private def getOrdersRoute(userIdentity: UserIdentity): Route = (get & pathEndOrSingleSlash) {
    val orders: Future[Vector[OrderSummary]] = orderingRegistryActor ? (GetOrders(userIdentity, _))
    complete(orders)
  }

  val getCardTypesRoute: Route = (get & path("cardtypes") & pathEndOrSingleSlash) {
    val cardTypes: Future[Vector[CardType]] = orderingRegistryActor ? GetCardTypes
    complete(cardTypes)
  }

  val getOrderRoute: Route = (get & path(JavaUUID) & pathEnd) { id =>
    val maybeOrder: Future[Option[Order]] = orderingRegistryActor ? (GetOrder(id, _))
    rejectEmptyResponse {
      complete(maybeOrder)
    }
  }

  private def doCancel(requestUuid: UUID, command: CancelOrderCommand)(implicit executor: ExecutionContext): Route = {
    val result: Future[Boolean] = orderingRegistryActor ? (CancelOrder(command, requestUuid, _))
    val statusCode = result.map {
      case true => StatusCodes.OK
      case false => StatusCodes.BadRequest
    }
    complete(statusCode)
  }

  val cancelOrderRoute: Route = (put & path("cancel") & pathEndOrSingleSlash & extractExecutionContext) { implicit executor =>
    (headerValueByName("x-requestid") & entity(as[CancelOrderCommand])) { (requestId, command) =>
      Try(UUID.fromString(requestId)) match {
        case Failure(ex) => reject(ValidationRejection(s"Invalid request id: $requestId", Some(ex)))
        case Success(requestUuid) => doCancel(requestUuid, command)
      }
    }
  }

  private def doShip(requestUuid: UUID, command: ShipOrderCommand)(implicit executor: ExecutionContext): Route = {
    val result: Future[Boolean] = orderingRegistryActor ? (ShipOrder(command, requestUuid, _))
    val statusCode = result.map {
      case true => StatusCodes.OK
      case false => StatusCodes.BadRequest
    }
    complete(statusCode)
  }

  val shipOrderRoute: Route = (put & path("ship") & pathEndOrSingleSlash & extractExecutionContext) { implicit executor =>
    (headerValueByName("x-requestid") & entity(as[ShipOrderCommand])) { (requestId, command) =>
      Try(UUID.fromString(requestId)) match {
        case Failure(ex) => reject(ValidationRejection(s"Invalid request id: $requestId", Some(ex)))
        case Success(requestUuid) => doShip(requestUuid, command)
      }
    }
  }
}
