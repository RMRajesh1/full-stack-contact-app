package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}
import workflow._

class ContactAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        var response:JsValue = null
        if (id.equals("contacts")) {
            val contactWorkflow = new ContactWorkflow()
            val contacts = contactWorkflow.getAllContacts()
            val numberWorkflow = new NumberWorkflow()
            val numbers = numberWorkflow.getAllNumbers()
            response = Json.toJson(Map("contacts" -> contacts, "numbers" -> numbers))
        } else {
            val workflow = new ContactWorkflow()
            val contact = workflow.getContact(id)
            response = Json.toJson(Map("contact" -> contact))
        }
        sendJsonResponse(req, resp, response)
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val updatedContact = payloadData(req, resp, "contact")
        val id = getEndUrlPattern(req, resp)
        val workflow = new ContactWorkflow()
        val updated = workflow.updateContact(id, updatedContact)
        val response: JsValue = Json.toJson(Map("contact" -> updated))
        sendJsonResponse(req, resp, response)
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        val workflow = new ContactWorkflow()
        val status = workflow.deleteContact(id)
        val response:JsValue = Json.toJson(Map("contact" -> status))
        sendJsonResponse(req, resp, response)
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val newContact = payloadData(req, resp, "contact")
        val workflow = new ContactWorkflow()
        val contact = workflow.createContact(newContact)
        val response: JsValue = Json.toJson(Map("contact" -> contact))
        sendJsonResponse(req, resp, response)
    }

}
