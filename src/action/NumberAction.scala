package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.{ListBuffer, Map}
import model.Number
import model.User
import workflow.NumberWorkflow

class NumberAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val account = req.getParameter("account")
        val contact = req.getParameter("contact")
        val workflow = new NumberWorkflow()
        val numberList = workflow.getAllNumbers(account, contact)
        val numbers = numberList.map(item => getValuesAsMap(item))
        val response = Json.toJson(Map("number" -> numbers))
        sendJsonResponse(req, resp, response)
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val updatedNumber = payloadData(req, resp, "number")
        val id = getEndUrlPattern(req, resp)
        val number = setValues(updatedNumber)
        number.id = id
        val workflow = new NumberWorkflow()
        workflow.updateNumber(number)
        val response:JsValue = Json.toJson(Map("number" -> getValuesAsMap(number)))
        sendJsonResponse(req, resp, response)
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val newNumber = payloadData(req, resp, "number")
        val number = setValues(newNumber)
        val workflow = new NumberWorkflow()
        workflow.createNumber(number)
        val response: JsValue = Json.toJson(Map("number" -> getValuesAsMap(number)))
        sendJsonResponse(req, resp, response)
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val id = getEndUrlPattern(req, resp)
        val number = Number()
        number.id = id
        val workflow = new NumberWorkflow()
        val status = workflow.deleteNumber(number)
        val response:JsValue = Json.toJson(Map("isDeleted" -> status))
        sendJsonResponse(req, resp, response)
    }

    def setValues(data: JsValue, isNewEntry: Boolean = false): Number ={
        val number = Number()
        if (!isNewEntry) { number.contact = data("contact").as[String] }
        number.number = data("number").as[String].toLong
        number.numberType = data("type").as[Int]
        number.user = data("user").as[String]
        number
    }

    def getValuesAsMap(number: Number): Map[String, String] = {
        Map(
            "id" -> number.id,
            "number" -> number.number.toString,
            "type" -> number.numberType.toString,
            "contact" -> number.contact,
            "user" -> number.user
        )
    }

}