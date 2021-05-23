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
        val numbers = ListBuffer[Map[String, String]]()
        numberList.foreach {
            case item => {
                numbers += getValuesAsMap(item)
            }
        }
        val response = Json.toJson(Map("number" -> numbers))
        sendJsonResponse(req, resp, response)
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val updatedNumber = payloadData(req, resp, "number")
        val id = getEndUrlPattern(req, resp)
        val number = Number()
        setValues(number, updatedNumber)
        number.id = id
        val workflow = new NumberWorkflow()
        workflow.updateNumber(number)
        val response:JsValue = Json.toJson(Map("number" -> getValuesAsMap(number)))
        sendJsonResponse(req, resp, response)
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val newNumber = payloadData(req, resp, "number")
        println("number user : "+req.getParameter("user"))
        val number = Number()
        setValues(number, newNumber)
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

    def setValues(number: Number, data: JsValue, isNewEntry: Boolean = false) {
        if (!isNewEntry) { number.contact = data("contact").as[String] }
        number.number = data("number").as[String].toLong
        number.numberType = data("type").as[Int]
        number.user = data("user").as[String]
    }

    def getValuesAsMap(number: Number): Map[String, String] = {
        val map = Map.empty[String, String]
        map("id") = number.id
        map("number") = number.number.toString
        map("type") = number.numberType.toString
        map("contact") = number.contact
        map("user") = number.user
        map
    }

}