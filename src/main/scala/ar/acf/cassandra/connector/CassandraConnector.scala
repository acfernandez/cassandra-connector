package ar.acf.cassandra.connector

import scala.concurrent.Future
import scala.concurrent.blocking
import com.datastax.driver.core.Cluster
import com.newzly.phantom.Implicits.context
import com.newzly.phantom.CassandraTable
import com.datastax.driver.core.Session

object CassandraConnector {
  
  val keySpace = "mykeyspace"

  lazy val cluster =  Cluster.builder()
    .addContactPoint("localhost")
    .withPort(9042)
    .withoutJMXReporting()
    .withoutMetrics()
    .build()

  lazy val session = blocking {
    cluster.connect(keySpace)
  }
  
}

trait CassandraConnector {
  self: CassandraTable[_, _] =>

  def createTable(): Future[Unit] = {
    create.future() map (_ => ())
  }

  implicit lazy val datastax: Session = CassandraConnector.session
}