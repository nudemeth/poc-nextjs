package nudemeth.poc.ordering.infrastructure

import com.outworkers.phantom.connectors.{ CassandraConnection, ContactPoints }
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object Connector {
  private val config = ConfigFactory.load()
  private val hosts = config.getStringList("cassandra.host").asScala
  private val keyspace = config.getString("cassandra.keyspace")
  private val username = config.getString("cassandra.username")
  private val password = config.getString("cassandra.password")

  lazy val connector: CassandraConnection = ContactPoints(hosts)
    .withClusterBuilder(
      _.withCredentials(username, password))
    .keySpace(keyspace)
}
