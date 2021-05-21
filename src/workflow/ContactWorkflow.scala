package workflow

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import model._
import bean._

class ContactWorkflow extends HelperWorkflow {

    def createContact(contact:JsValue): Map[String, String] = {
        val contactModel = new ContactModel()
        setValues(contactModel, contact)
        contactModel.id = generateRandomId()
        val db = new ContactBean()
        val isSuccess = db.createContact(contactModel)
        var response = Map.empty[String, String]
        if (isSuccess) {
            getValuesAsMap(contactModel, response)
        }   else {
            response("status") = "failed"
        }
        response
    }

    def getAllContacts(): ListBuffer[Map[String, String]] = {
        val db = new ContactBean()
        val contactList = db.getAllContacts()
        val contacts = ListBuffer[Map[String, String]]()
        contactList.foreach {
            case item => {
                contacts += getValuesAsMap(item)
            }
        }
        contacts
    }

    def getContact(id:String): Map[String, String] = {
        val contact = new ContactModel()
        val db = new ContactBean()
        db.getContact(id, contact)
        getValuesAsMap(contact)
    }

    def updateContact(id:String, updatedContact:JsValue): Map[String, String] = {
        val contact = new ContactModel()
        contact.id = id
        setValues(contact, updatedContact)
        val db = new ContactBean()
        val isSuccess = db.updateContact(contact)
        var response = Map.empty[String, String]
        if (isSuccess) {
            getValuesAsMap(contact, response)
        } else {
            response("status") = "failed"
        }
        response
    }

    def deleteContact(id: String): Map[String, String] = {
        var response = Map.empty[String, String]
        val db = new ContactBean()
        val isSuccess = db.deleteContact(id)
        if (isSuccess) {
            response("status") = "success"
        } else {
            response("status") = "failed"
        }
        response
    }

    def setValues(contact: ContactModel, data: JsValue) {
        if (data("image").hashCode() != -2067765616) {
            contact.image = data("image").as[String]
        }
        if (data("email").hashCode() != -2067765616) {
            contact.email = data("email").as[String]
        }
        if (data("description").hashCode() != -2067765616) {
            contact.description = data("description").as[String]
        }
        contact.name = data("name").as[String]
        contact.date = data("date").as[String]
    }

    def getValuesAsMap(contact: ContactModel, map: Map[String, String] = Map.empty): Map[String, String] = {
        map("id") = contact.id
        map("image") = contact.image
        map("name") = contact.name
        map("email") = contact.email
        map("description") = contact.description
        map("date") = contact.date
        map
    }

}