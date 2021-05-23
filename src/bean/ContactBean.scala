package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import model.Contact
import model.User

class ContactBean extends DBManager {
    def createContact(contact: Contact): Boolean = {
        var status = false
        var connection: Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "INSERT INTO contact(contact_id, image, name, email, description, date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)"
            val statement = connection.prepareStatement(query)
            statement.setString(1, contact.id)
            statement.setString(2, contact.image)
            statement.setString(3, contact.name)
            statement.setString(4, contact.email)
            statement.setString(5, contact.description)
            statement.setLong(6, contact.date)
            statement.setString(7, contact.user)
            statement.executeUpdate()
            status = true
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        status
    }

    def getAllContacts(user: User): ListBuffer[Contact] = {
        var contacts = ListBuffer[Contact]()
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "SELECT * FROM contact WHERE user_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, user.id)
            val result = statement.executeQuery()
            while (result.next()) {
                val contact = new Contact()
                contact.id = result.getString(1)
                contact.image = result.getString(2)
                contact.name = result.getString(3)
                contact.email = result.getString(4)
                contact.description = result.getString(5)
                contact.date = result.getLong(6)
                contact.user = result.getString(7)
                contacts += contact
            }
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        contacts
    }

    def getContact(contact: Contact) {
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "SELECT * FROM contact  WHERE contact_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, contact.id)
            val result = statement.executeQuery()
            while (result.next()) {
                contact.id = result.getString(1)
                contact.image = result.getString(2)
                contact.name = result.getString(3)
                contact.email = result.getString(4)
                contact.description = result.getString(5)
                contact.date = result.getLong(6)
                contact.user = result.getString(7)
            }
        }
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
    }

    def updateContact(contact: Contact): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
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
        catch { case exception: Exception => println(exception.printStackTrace()) }
        finally { closeConnection(connection) }
        status
    }

    def deleteContact(contact: Contact): Boolean = {
        var status = false
        var connection:Connection = null
        try {
            connection = DriverManager.getConnection(url, dbUser, password)
            val query = "DELETE FROM contact WHERE contact_id = ?"
            val statement = connection.prepareStatement(query)
            statement.setString(1, contact.id)
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
