import java.sql._
import play.api.libs.json._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

class DBManager {
    Class.forName("org.postgresql.Driver");
    val url = "jdbc:postgresql://localhost:5432/contact_application"
    val user = "postgres"
    val password = ""

    def getContactList(): JsValue = {
        var contactList:JsValue = null
        var dbConnection:Connection = null
        try {
            dbConnection = DriverManager.getConnection(url, user, password)

            val query1 = "SELECT * FROM contact"
            val statement1 = dbConnection.prepareStatement(query1)
            val contactResult = statement1.executeQuery()
            val contactContainer = ListBuffer[Map[String, String]]()
            while (contactResult.next()) {
                val contactMap = Map.empty[String, String]
                contactMap("id") = contactResult.getObject(1).toString
                contactMap("image") = contactResult.getObject(2).toString
                contactMap("name") = contactResult.getObject(3).toString
                contactMap("email") = contactResult.getObject(4).toString
                contactMap("description") = contactResult.getObject(5).toString
                contactMap("date") = contactResult.getObject(6).toString
                contactContainer += contactMap
            }

            val query2 = "SELECT * FROM number"
            val statement2 = dbConnection.prepareStatement(query2)
            val numberResult = statement2.executeQuery()
            val numberContainer = ListBuffer[Map[String, String]]()
            while (numberResult.next()) {
                val numberMap = Map.empty[String, String]
                numberMap("id") = numberResult.getObject(1).toString
                numberMap("number") = numberResult.getObject(2).toString
                numberMap("type") = numberResult.getObject(3).toString
                numberMap("contact") = numberResult.getObject(4).toString
                numberContainer += numberMap
            }
            
            val contactListMap = Map("contacts" -> contactContainer, "numbers" -> numberContainer)
            contactList = Json.toJson(contactListMap)
            contactList
        }
        catch {
            case exception:Exception => {
                println(exception.printStackTrace())
                contactList
            }
        }
        finally {
            if (dbConnection != null) {
                dbConnection.close()
            }
            contactList
        }
    }

    def getContact(contactId:String): JsValue = {
        var contactData:JsValue = null
        var dbConnection:Connection = null
        try {
            dbConnection = DriverManager.getConnection(url, user, password)

            val query1 = "SELECT * FROM contact WHERE contact_id = ?"
            val statement1 = dbConnection.prepareStatement(query1)
            statement1.setString(1, contactId)
            val contactResult = statement1.executeQuery()
            val contactContainer = ListBuffer[Map[String, String]]()
            val contactMap = Map.empty[String, String]
            while (contactResult.next()) {
                val contactMap = Map.empty[String, String]
                contactMap("id") = contactResult.getObject(1).toString
                contactMap("image") = contactResult.getObject(2).toString
                contactMap("name") = contactResult.getObject(3).toString
                contactMap("email") = contactResult.getObject(4).toString
                contactMap("description") = contactResult.getObject(5).toString
                contactMap("date") = contactResult.getObject(6).toString
                contactContainer += contactMap
            }

            val query2 = "SELECT * FROM number WHERE contact_id = ?"
            val statement2 = dbConnection.prepareStatement(query2)
            statement2.setString(1, contactId)
            val numberResult = statement2.executeQuery()
            val numberContainer = ListBuffer[Map[String, String]]()
            while (numberResult.next()) {
                val numberMap = Map.empty[String, String]
                numberMap("id") = numberResult.getObject(1).toString
                numberMap("number") = numberResult.getObject(2).toString
                numberMap("type") = numberResult.getObject(3).toString
                numberMap("contact") = numberResult.getObject(4).toString
                numberContainer += numberMap
            }

            val contacDataContainer = Map("contacts" -> contactContainer, "numbers" -> numberContainer)
            contactData = Json.toJson(contacDataContainer)

            contactData
        }
        catch {
            case exception:Exception => {
                println(exception.printStackTrace)
                contactData
            }
        }
        finally {
            if (dbConnection != null) {
                dbConnection.close()
            }
            contactData
        }
    }


}