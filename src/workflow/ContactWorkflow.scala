package workflow

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import bean._

class ContactWorkflow {

    def getAllContacts(): ListBuffer[Map[String, String]] = {
        val db = new DBManagerBean()
        db.getAllContacts()
    }

    def getContact(id:Int): ListBuffer[Map[String, String]] = {
        val db = new DBManagerBean()
        db.getContact(id)
    }

    def updateContact(id:Int, updatedContact:JsValue): Map[String, String] = {
        val db = new DBManagerBean()
        db.updateContact(id, updatedContact)
    }

    def createContact(contact:JsValue): Map[String, String] = {
        val db = new DBManagerBean()
        db.createContact(contact)
    }

    def deleteContact(id:Int): Map[String, String] = {
        val db = new DBManagerBean()
        db.deleteContact(id)
    }

}