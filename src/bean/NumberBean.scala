package bean

import java.sql.{Connection, DriverManager, PreparedStatement}
import scala.collection.mutable.{ListBuffer, Map}
import model.Number
import model.User

class NumberBean extends DBManager {

    def createNumber(number: Number): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "INSERT INTO number(number_id, number, type, contact_id, user_id) VALUES (?, ?, ?, ?, ?)"
            val statement = connection.prepareStatement(query)
            statement.setString(1, number.id)
            statement.setLong(2, number.number)
            statement.setInt(3, number.numberType)
            statement.setString(4, number.contact)
            statement.setString(5, number.user)
            statement.executeUpdate()
            status = true
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        status
    }

    def getAllNumbers(userId: String, contactId: String): ListBuffer[Number] = {
        var numbers = ListBuffer[Number]()
        var connection: Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            var statement: PreparedStatement = null
            if (contactId != null) {
                val query = "SELECT * FROM number WHERE user_id = ? AND contact_id = ?"
                statement = connection.prepareStatement(query)
                statement.setString(1, userId)
                statement.setString(2, contactId)
            } else {
                val query = "SELECT * FROM number WHERE user_id = ?"
                statement = connection.prepareStatement(query)
                statement.setString(1, userId)
            }
            val result = statement.executeQuery()
            while (result.next()) {
                val number = new Number()
                number.id = result.getString(1)
                number.number = result.getLong(2)
                number.numberType = result.getInt(3)
                number.contact = result.getString(4)
                number.user = result.getString(5)
                numbers += number
            }
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        numbers
    }

    def updateNumber(number: Number): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "UPDATE number SET number = ?, type = ? WHERE number_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setLong(1, number.number)
            statement.setInt(2, number.numberType)
            statement.setString(3, number.id)
            statement.executeUpdate()
            status = true
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        status
    }

    def deleteNumber(number: Number): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "DELETE FROM number WHERE number_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, number.id)
            val deletedRowCount = statement.executeUpdate()
            if (deletedRowCount > 0) {
                status = true
            } else {
                throw new Exception("failed to delete")
            }
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        status
    }

}
