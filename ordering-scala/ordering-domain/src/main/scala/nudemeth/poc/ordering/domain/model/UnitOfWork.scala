package nudemeth.poc.ordering.domain.model

import scala.concurrent.Future

trait UnitOfWork {
  def saveChangeAsync(): Future[Int]
  def saveEntitiesAsync(): Future[Boolean]
}
