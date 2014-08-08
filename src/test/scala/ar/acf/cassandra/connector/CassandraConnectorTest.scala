

package ar.acf.cassandra.connector

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

import org.junit.Test
import org.junit.Assert._


class CassandraConnectorTest {
  
  @Test def incrementOk {
    val f = Record.increment("home", 1)
    val result = Await.result(f, 1000 millis)
    assertTrue(result.all().isEmpty())
  }
  
  @Test def decrementOk {
    val f = Record.increment("home", -1)
    val result = Await.result(f, 1000 millis)
    assertTrue(result.all().isEmpty())
  }
  
  @Test def readOk {
    val f = Record.read("home")
    val result = Await.result(f, 1000 millis)
    assertEquals(result.get.name, "home")
    assertTrue(result.get.count > 0)
  }
  
  @Test def readError {
    val f = Record.read("dskjfskdj")
    val result = Await.result(f, 1000 millis)
    assertTrue(result.isEmpty)
  }
}