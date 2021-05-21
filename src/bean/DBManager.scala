package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._

class DBManager {
    val url = "jdbc:postgresql://localhost:5432/contact_application"
    val user = "postgres"
    val password = ""
    Class.forName("org.postgresql.Driver")
}
