package connector

import scala.concurrent.Future

import com.datastax.driver.core.{ ResultSet, Row }
import com.newzly.phantom.CassandraTable
import com.newzly.phantom.Implicits._

import connectors.CassandraConnector


case class Model(
  name: String,
  count: Long
)

sealed class Record extends CassandraTable[Record, Model] {

  object name extends StringColumn(this) with PrimaryKey[String]
  object count_entries extends CounterColumn(this)
  
  def fromRow(row: Row): Model = {
    Model(name(row), count_entries(row))
  }
}

object Record extends Record with CassandraConnector {
  
  override lazy val tableName = "records"
  
  def read(id: String): Future[Option[Model]] = select.where(_.name eqs id).one
  
  def increment(id: String, number: Long): Future[ResultSet] = CounterBatchStatement()
    .add(Record.update.where(_.name eqs id).modify(_.count_entries increment number))
    .future
    
}