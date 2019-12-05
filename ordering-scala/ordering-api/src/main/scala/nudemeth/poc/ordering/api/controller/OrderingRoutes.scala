package nudemeth.poc.ordering.api.controller

import java.util.UUID

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives.{ get, post }
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.{ Route, ValidationRejection }
import akka.http.scaladsl.server.directives.Credentials
import akka.pattern.ask
import akka.util.Timeout
import nudemeth.poc.ordering.api.application.command.{ CancelOrderCommand, ShipOrderCommand }
import nudemeth.poc.ordering.api.application.query.viewmodel.{ CardType, Order, OrderSummary }
import nudemeth.poc.ordering.api.controller.OrderingRegistryActor._
import nudemeth.poc.ordering.api.infrastructure.service.IdentityService.{ ExtractUserIdentity, UserIdentity }

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.duration._
import scala.util.{ Failure, Success, Try }

trait OrderingRoutes extends JsonSupport {
  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem
  lazy val log = Logging(system, classOf[OrderingRoutes])
  // other dependencies that APIRoutes use
  def orderingRegistryActor: ActorRef
  def identityRegistryActor: ActorRef
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
      case Credentials.Provided(token) => (identityRegistryActor ? ExtractUserIdentity(token)).mapTo[Option[UserIdentity]]
      case _ => Future.successful(None)
    }
  }

  private def getOrdersRoute(userIdentity: UserIdentity): Route = get {
    pathEndOrSingleSlash {
      val orders: Future[Vector[OrderSummary]] = (orderingRegistryActor ? GetOrders(userIdentity)).mapTo[Vector[OrderSummary]]
      complete(orders)
    }
  }

  val getCardTypesRoute: Route = get {
    path("cardtypes") {
      pathEndOrSingleSlash {
        val cardTypes: Future[Vector[CardType]] = (orderingRegistryActor ? GetCardTypes()).mapTo[Vector[CardType]]
        complete(cardTypes)
      }
    }
  }

  val getOrderRoute: Route = get {
    path(JavaUUID) { id =>
      val maybeOrder: Future[Option[Order]] = (orderingRegistryActor ? GetOrder(id)).mapTo[Option[Order]]
      rejectEmptyResponse {
        complete(maybeOrder)
      }
    }
  }

  private def doCancel(requestUuid: UUID, command: CancelOrderCommand)(implicit executor: ExecutionContext): Route = {
    val statusCode = (orderingRegistryActor ? CancelOrder(command, requestUuid)).mapTo[Boolean].map {
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
    val statusCode = (orderingRegistryActor ? ShipOrder(command, requestUuid)).mapTo[Boolean].map {
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
