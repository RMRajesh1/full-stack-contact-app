package bean

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._

class DBManagerBean {

  val url = "jdbc:postgresql://localhost:5432/contact_application"
  val user = "postgres"
  val password = ""

  Class.forName("org.postgresql.Driver")

  def getAllContacts(): ListBuffer[Map[String, String]] = {
    var contacts = ListBuffer[Map[String, String]]()
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "SELECT * FROM contact"
      val statement = connection.prepareStatement(query)
      val result = statement.executeQuery()
      while (result.next()) {
        val contactMap = Map.empty[String, String]
        contactMap("id") = result.getObject(1).toString
        contactMap("image") = result.getObject(2).toString
        contactMap("name") = result.getObject(3).toString
        contactMap("email") = result.getObject(4).toString
        contactMap("description") = result.getObject(5).toString
        contactMap("date") = result.getObject(6).toString
        contacts += contactMap
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

  def getAllNumbers(): ListBuffer[Map[String, String]] = {
    var numbers = ListBuffer[Map[String, String]]()
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "SELECT * FROM number"
      val statement = connection.prepareStatement(query)
      val result = statement.executeQuery()
      while (result.next()) {
        val numberMap = Map.empty[String, String]
        numberMap("id") = result.getObject(1).toString
        numberMap("number") = result.getObject(2).toString
        numberMap("type") = result.getObject(3).toString
        numberMap("contact") = result.getObject(4).toString
        numbers += numberMap
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

  def getContact(id:Int): ListBuffer[Map[String, String]] = {
    var contact = ListBuffer[Map[String, String]]()
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "SELECT * FROM contact  WHERE contact_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setInt(1, id)
      val result = statement.executeQuery()
      while (result.next()) {
        val contactMap = Map.empty[String, String]
        contactMap("id") = result.getObject(1).toString
        contactMap("image") = result.getObject(2).toString
        contactMap("name") = result.getObject(3).toString
        contactMap("email") = result.getObject(4).toString
        contactMap("description") = result.getObject(5).toString
        contactMap("date") = result.getObject(6).toString
        contact += contactMap
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
    contact
  }

  def getNumber(id:Int): ListBuffer[Map[String, String]] = {
    var number = ListBuffer[Map[String, String]]()
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "SELECT * FROM number  WHERE contact_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setInt(1, id)
      val result = statement.executeQuery()
      while (result.next()) {
        val numberMap = Map.empty[String, String]
        numberMap("id") = result.getObject(1).toString
        numberMap("number") = result.getObject(2).toString
        numberMap("type") = result.getObject(3).toString
        numberMap("contact") = result.getObject(4).toString
        number += numberMap
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
    number
  }

  def updateContact(id:Int, updatedContact:JsValue): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "UPDATE contact SET image = ?, name = ?, email = ?, description = ?, date = ? WHERE contact_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setString(1, updatedContact("image").as[String])
      statement.setString(2, updatedContact("name").as[String])
      statement.setString(3, updatedContact("email").as[String])
      statement.setString(4, updatedContact("description").as[String])
      statement.setLong(5, updatedContact("date").as[String].toLong) // error
      statement.setInt(6, id)
      statement.executeUpdate()
      status("status") = "success"
    }
    catch {
      case exception: Exception => {
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

  def updateNumber(id:Int, updatedNumber:JsValue): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "UPDATE number SET number = ?, type = ? WHERE number_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setLong(1, updatedNumber("number").as[String].toLong)  
      statement.setInt(2, updatedNumber("type").as[Int])
      statement.setInt(3, id)
      statement.executeUpdate()
      status("status") = "success"
    }
    catch {
      case exception: Exception => {
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

  def createContact(contact:JsValue): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val name = contact("name").as[String]
      val date = contact("date").as[String].toLong
      var image = ""
      if (contact("image").hashCode() != -2067765616) {
        image = contact("image").as[String]
      }
      var email = ""
      if (contact("email").hashCode() != -2067765616) {
        email = contact("email").as[String]
      }
      var description = ""
      if (contact("description").hashCode() != -2067765616) {
        description = contact("description").as[String]
      }
      val query = s"INSERT INTO contact(image, name, email, description, date) VALUES ('${image}', '${name}', '${email}', '${description}', '${date}')"
      val statement = connection.createStatement()
      statement.executeUpdate(query)
      val query2 = s"SELECT contact_id FROM contact WHERE name = '${name}' ORDER BY name DESC LIMIT 1"
      val statement2 = connection.createStatement()
      val result = statement2.executeQuery(query2)
      while (result.next()) {
        status("id") = result.getInt(1).toString()
      }
    }
    catch {
      case exception: Exception => {
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

  def createNumber(number:JsValue): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val contactNumber = number("number").as[String].toLong
      val numberType = number("type").toString().toInt
      var contactId = -1
      if (number("contact").hashCode() == -2067765616) {
        contactId = getAtMostContactId()
      } else {
        contactId = number("contact").as[String].toInt
      }
      val query = s"INSERT INTO number(number, type, contact_id) VALUES (${contactNumber}, ${numberType}, ${contactId})"
      val statement = connection.createStatement()
      statement.executeUpdate(query)
      val query2 = s"SELECT number_id FROM number WHERE number = ${contactNumber} ORDER BY number DESC LIMIT 1 "
      val statement2 = connection.createStatement()
      val result = statement2.executeQuery(query2)
      while (result.next()) {
        status("id") = result.getInt(1).toString()
      }
    }
    catch {
      case exception: Exception => {
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

  def deleteContact(id:Int): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "DELETE FROM contact WHERE contact_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setInt(1, id)
      val deletedRowCount = statement.executeUpdate()
      if (deletedRowCount > 0) {
        status("status") = "success"
      } else {
        throw new Exception("failed to delete")
      }
    }
    catch {
      case exception: Exception => {
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

  def deleteNumber(id:Int): Map[String, String] = {
    val status = Map.empty[String, String]
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "DELETE FROM number WHERE number_id = ?"
      val statement = connection.prepareStatement(query)
      statement.setInt(1, id)
      val deletedRowCount = statement.executeUpdate()
      if (deletedRowCount > 0) {
        status("status") = "success"
      } else {
        throw new Exception("failed to delete")
      }
    }
    catch {
      case exception: Exception => {
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

  def getAtMostContactId(): Int = {
    var atMostContactId = -1
    var connection:Connection = null
    try {
      connection = DriverManager.getConnection(url, user, password)
      val query = "SELECT contact_id FROM contact ORDER BY contact_id DESC LIMIT 1"
      val statement = connection.createStatement()
      val result = statement.executeQuery(query)
      while (result.next()) {
        atMostContactId = result.getInt(1)
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

}
