package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.{ListBuffer, Map}
import model.Contact
import model.User
import workflow.ContactWorkflow

class ContactAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        var response: JsValue = null
        if (id.equals("contacts")) {
            val user = User()
            user.id = req.getParameter("account")
            val contactWorkflow = new ContactWorkflow()
            val contactList = contactWorkflow.getAllContacts(user)
            val contacts = ListBuffer[Map[String, String]]()
            contactList.foreach {
                case item => {
                    contacts += getValuesAsMap(item)
                }
            }
            response = Json.toJson(Map("contacts" -> contacts))
        } else {
            val contact = Contact()
            contact.id = id
            val workflow = new ContactWorkflow()
            workflow.getContact(contact)
            response = Json.toJson(Map("contact" -> getValuesAsMap(contact)))
        }
        sendJsonResponse(req, resp, response)
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val updatedContact = payloadData(req, resp, "contact")
        val id = getEndUrlPattern(req, resp)
        val contact = Contact()
        setValues(contact, updatedContact)
        contact.id = id
        val workflow = new ContactWorkflow()
        workflow.updateContact(contact)
        val response: JsValue = Json.toJson(Map("contact" -> getValuesAsMap(contact)))
        sendJsonResponse(req, resp, response)
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        val contact = Contact()
        contact.id = id
        val workflow = new ContactWorkflow()
        val status = workflow.deleteContact(contact)
        val response:JsValue = Json.toJson(Map("isDeleted" -> status))
        sendJsonResponse(req, resp, response)
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val newContact = payloadData(req, resp, "contact")
        val contact = Contact()
        setValues(contact, newContact)
        val workflow = new ContactWorkflow()
        workflow.createContact(contact)
        val response: JsValue = Json.toJson(Map("contact" -> getValuesAsMap(contact)))
        sendJsonResponse(req, resp, response)
    }

    def setValues(contact: Contact, data: JsValue) {
        if (data("image").hashCode() != -2067765616) { contact.image = data("image").as[String] }
        if (data("email").hashCode() != -2067765616) { contact.email = data("email").as[String] }
        if (data("description").hashCode() != -2067765616) { contact.description = data("description").as[String] }
        contact.name = data("name").as[String]
        contact.date = data("date").as[String].toLong
        contact.user = data("user").as[String]
    }

    def getValuesAsMap(contact: Contact): Map[String, String] = {
        val map = Map.empty[String, String]
        map("id") = contact.id
        map("image") = contact.image
        map("name") = contact.name
        map("email") = contact.email
        map("description") = contact.description
        map("date") = contact.date.toString
        map("user") = contact.user
        map
    }

}
