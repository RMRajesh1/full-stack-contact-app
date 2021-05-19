package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._

class UsersBean {

    val url = "jdbc:postgresql://localhost:5432/contact_application"
    val user = "postgres"
    val password = ""

    Class.forName("org.postgresql.Driver")

    def signupUser(userData:Map[String, String]): Map[String, String] = {
        val status = Map.empty[String, String]
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "INSERT INTO users(name, email, password, created_date) VALUES (?, ?, ?, ?)"
            val statement = connection.prepareStatement(query)
            statement.setString(1, userData("name"))
            statement.setString(2, userData("email"))
            statement.setString(3, userData("password"))
            statement.setLong(4, userData("createdDate").toLong)
            statement.executeUpdate()
            status("status") = "success"
        }
        catch {
            case exception:Exception => {
                println(exception.printStackTrace())
                status("status") = "failed"
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        status
    }

    def loginUser(email:String, password:String): Map[String, String] = {
        val user = Map.empty[String, String]
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "SELECT * FROM users WHERE email = ? AND password = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, email)
            statement.setString(1, password)
            val result = statement.executeQuery()
            while (result.next()) {
                user("id") = result.getInt(1).toString()
                user("name") = result.getString(2)
                user("email") = result.getString(3)
                user("password") = result.getString(4)
                user("created_date") = result.getLong(5).toString()
            }
        }
        catch {
            case exception:Exception => {
                println(exception.printStackTrace())
                user("isError") = "true"
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        status
    }

}