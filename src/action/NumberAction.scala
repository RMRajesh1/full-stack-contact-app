package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}
import workflow._

class NumberAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val contact = req.getParameter("contact")
        var response:JsValue = null
        val workflow = new NumberWorkflow()
        if (contact != null) {
            val number = workflow.getAllNumbers(contact)
            response = Json.toJson(Map("number" -> number))
        } else {
            val id = getEndUrlPattern(req, resp)
            val number = workflow.getNumber(id)
            response = Json.toJson(Map("number" -> number))
        }
        sendJsonResponse(req, resp, response)
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val updatedNumber = payloadData(req, resp, "number")
        val id = getEndUrlPattern(req, resp)
        val workflow = new NumberWorkflow()
        val updated = workflow.updateNumber(id, updatedNumber)
        val response:JsValue = Json.toJson(Map("number" -> updated))
        sendJsonResponse(req, resp, response)
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val newNumber = payloadData(req, resp, "number")
        val workflow = new NumberWorkflow()
        val number = workflow.createNumber(newNumber)
        val response: JsValue = Json.toJson(Map("number" -> number))
        sendJsonResponse(req, resp, response)
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        val workflow = new NumberWorkflow()
        val status = workflow.deleteNumber(id)
        val response:JsValue = Json.toJson(Map("number" -> status))
        sendJsonResponse(req, resp, response)
    }

}