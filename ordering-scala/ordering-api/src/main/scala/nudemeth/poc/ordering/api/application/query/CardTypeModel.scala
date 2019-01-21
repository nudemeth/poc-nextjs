package nudemeth.poc.ordering.api.application.query

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.Table
import com.outworkers.phantom.keys.PartitionKey

import scala.concurrent.Future

abstract class CardTypeModel extends Table[CardTypeModel, CardTypeEntity] {
  override def tableName: String = "CardType"

  object name extends Col[String] with PartitionKey

  def getByName(name: String): Future[Option[CardTypeEntity]] = {
    select
      .where(_.name eqs name)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def list(): Future[Vector[CardTypeEntity]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
      .mapTo[Vector[CardTypeEntity]]
  }
}
