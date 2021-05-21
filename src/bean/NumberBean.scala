package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import model._

class NumberBean extends DBManager {
    def createNumber(number: NumberModel): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            number.contact = getAtMostContactId()
            val query = s"INSERT INTO number(number_id, number, type, contact_id) VALUES ('${number.id}', ${number.number.toLong}, ${number.numberType.toInt}, '${number.contact}')"
            val statement = connection.createStatement()
            statement.executeUpdate(query)
            status = true
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        status
    }

    def getAllNumbers(contactId: String): ListBuffer[NumberModel] = {
        var numbers = ListBuffer[NumberModel]()
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            var query = "SELECT * FROM number"
            if (contactId != null) {
                query = s"SELECT * FROM number WHERE contact_id = '${contactId}'"
            }
            val statement = connection.prepareStatement(query)
            val result = statement.executeQuery()
            while (result.next()) {
                val numberModel = new NumberModel()
                numberModel.id = result.getObject(1).toString
                numberModel.number = result.getObject(2).toString
                numberModel.numberType = result.getObject(3).toString
                numberModel.contact = result.getObject(4).toString
                numbers += numberModel
            }
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        numbers
    }

    def getAtMostContactId(): String = {
        var atMostContactId = ""
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "SELECT contact_id FROM contact ORDER BY contact_id DESC LIMIT 1"
            val statement = connection.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                atMostContactId = result.getString(1)
            }
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        atMostContactId
    }

    def getNumber(id: String, number: NumberModel) {
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "SELECT * FROM number  WHERE contact_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            val result = statement.executeQuery()
            while (result.next()) {
                number.id = result.getObject(1).toString
                number.number = result.getObject(2).toString
                number.numberType = result.getObject(3).toString
                number.contact = result.getObject(4).toString
            }
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
    }

    def updateNumber(number: NumberModel): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "UPDATE number SET number = ?, type = ? WHERE number_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setLong(1, number.number.toLong)
            statement.setInt(2, number.numberType.toInt)
            statement.setString(3, number.id)
            statement.executeUpdate()
            status = true
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
            }
        }
        finally {
            if (connection != null) {
                connection.close()
            }
        }
        status
    }

    def deleteNumber(id: String): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "DELETE FROM number WHERE number_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            val deletedRowCount = statement.executeUpdate()
            if (deletedRowCount > 0) {
                status = true
            } else {
                throw new Exception("failed to delete")
            }
        }
        catch {
            case exception: Exception => {
                println(exception.printStackTrace())
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
