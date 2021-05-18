import java.sql._
import play.api.libs.json._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

class DBManager {
    Class.forName("com.mysql.cj.jdbc.Driver");  
    val url = "jdbc:mysql://localhost:3306/contact_application"
    val user = "root"
    val password = "root"

    def getContactList() : JsValue = {
        var contactList:JsValue = null
        var dbConnection:Connection = null
        try {
            dbConnection = DriverManager.getConnection(url, user, password)
            val query1 = "SELECT * FROM contact"
            val statement1 = dbConnection.prepareStatement(query1)
            val contactResult = statement1.executeQuery()
            val contactContainer = ListBuffer[Map[String, String]]()
            val contactMap = Map.empty[String, String]
            while (contactResult.next()) {
                contactMap += "contact_id" -> contactResult.getObject(1).toString
                contactMap += "image" -> contactResult.getObject(2).toString
                contactMap += "name" -> contactResult.getObject(3).toString
                contactMap += "email" -> contactResult.getObject(4).toString
                contactMap += "description" -> contactResult.getObject(5).toString
                contactMap += "date" -> contactResult.getObject(6).toString
                contactContainer += contactMap
            }

            val query2 = "SELECT * FROM number"
            val statement2 = dbConnection.prepareStatement(query2)
            val numberResult = statement2.executeQuery()
            val numberContainer = ListBuffer[Map[String, String]]()
            val numberMap = Map.empty[String, String]
            while (numberResult.next()) {
                numberMap += "number_id" -> numberResult.getObject(1).toString
                numberMap += "number" -> numberResult.getObject(2).toString
                numberMap += "type" -> numberResult.getObject(3).toString
                numberContainer += numberMap
            }
            
            val contactListMap = Map("contact" -> contactContainer, "number" -> numberContainer)
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
}