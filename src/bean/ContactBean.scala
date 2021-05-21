package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import model._

class ContactBean extends DBManager {
    def createContact(contact: ContactModel): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = s"INSERT INTO contact(contact_id, image, name, email, description, date) VALUES ('${contact.id}', '${contact.image}', '${contact.name}', '${contact.email}', '${contact.description}', '${contact.date.toLong}')"
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

    def getAllContacts(): ListBuffer[ContactModel] = {
        var contacts = ListBuffer[ContactModel]()
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "SELECT * FROM contact"
            val statement = connection.prepareStatement(query)
            val result = statement.executeQuery()
            while (result.next()) {
                val contactModel = new ContactModel()
                contactModel.id = result.getObject(1).toString
                contactModel.image = result.getObject(2).toString
                contactModel.name = result.getObject(3).toString
                contactModel.email = result.getObject(4).toString
                contactModel.description = result.getObject(5).toString
                contactModel.date = result.getObject(6).toString
                contacts += contactModel
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
        contacts
    }

    def getContact(id: String, contact: ContactModel) {
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "SELECT * FROM contact  WHERE contact_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            val result = statement.executeQuery()
            while (result.next()) {
                contact.id = result.getObject(1).toString
                contact.image = result.getObject(2).toString
                contact.name = result.getObject(3).toString
                contact.email = result.getObject(4).toString
                contact.description = result.getObject(5).toString
                contact.date = result.getObject(6).toString
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

    def updateContact(contact: ContactModel): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "UPDATE contact SET image = ?, name = ?, email = ?, description = ?, date = ? WHERE contact_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, contact.image)
            statement.setString(2, contact.name)
            statement.setString(3, contact.email)
            statement.setString(4, contact.description)
            statement.setLong(5, contact.date.toLong)
            statement.setString(6, contact.id)
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

    def deleteContact(id: String): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, user, password)
            val query = "DELETE FROM contact WHERE contact_id = ?"
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
