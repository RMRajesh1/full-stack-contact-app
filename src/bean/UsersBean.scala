package bean

import java.sql.{Connection, DriverManager, PreparedStatement}
import scala.collection.mutable.{ListBuffer, Map}
import model.User

class UsersBean extends DBManager {

    def createUser(user: User) {
        val status = false
        var connection: Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "INSERT INTO users(user_id, name, email, password, created_date) VALUES (?, ?, ?, ?, ?)"
            val statement = connection.prepareStatement(query)
            statement.setString(1, user.id)
            statement.setString(2, user.name)
            statement.setString(3, user.email)
            statement.setString(4, user.password)
            statement.setLong(5, user.createdDate)
            statement.executeUpdate()
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
    }

    def getUser(user: User) {
        var connection: Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            var statement: PreparedStatement = null
            if (user.email != null) {
                val query = "SELECT * FROM users WHERE email = ?"
                statement = connection.prepareStatement(query)
                statement.setString(1, user.email)
            } else {
                val query = "SELECT * FROM users WHERE user_id = ?"
                statement = connection.prepareStatement(query)
                statement.setString(1, user.id)
            }
            val result = statement.executeQuery()
            while (result.next()) {
                user.id = result.getString(1)
                user.name = result.getString(2)
                user.email = result.getString(3)
                user.password = result.getString(4)
                user.createdDate = result.getLong(5)
            }
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
    }

}
