package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}
import model._

class NumberAction extends HttpServlet {

    override def doGet(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        val updatedNumber = payloadJson("number")

        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        val updatedNumber = payloadJson("number")

        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt

        val status = new NumberModel().updateNumber(id, updatedNumber)

        val data:JsValue = Json.toJson(Map("number" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(data))
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        val newNumber = payloadJson("number")

        val status = new NumberModel().createNumber(newNumber)

        val data:JsValue = Json.toJson(Map("number" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(data))
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt

        val status = new NumberModel().deleteNumber(id)

        val data:JsValue = Json.toJson(Map("number" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(data))
    }

}