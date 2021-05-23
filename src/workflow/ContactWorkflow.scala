package workflow

import scala.collection.mutable.{ListBuffer, Map}
import model.Contact
import model.User
import bean.ContactBean

class ContactWorkflow extends HelperWorkflow {

    def createContact(contact: Contact) {
        contact.id = generateRandomId()
        val db = new ContactBean()
        val isSuccess = db.createContact(contact)
    }

    def getAllContacts(user: User): ListBuffer[Contact] = {
        val db = new ContactBean()
        val contacts = db.getAllContacts(user)
        contacts
    }

    def getContact(contact: Contact) {
        val db = new ContactBean()
        db.getContact(contact)
    }

    def updateContact(contact: Contact) {
        val db = new ContactBean()
        db.updateContact(contact)
    }

    def deleteContact(contact: Contact): Boolean = {
        val db = new ContactBean()
        db.deleteContact(contact)
    }

}