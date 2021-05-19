package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}
import model._

class ContactAction extends HttpServlet {

    override def doGet(req: HSReq, resp: HSResp) {
        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt

        val contact = new ContactModel().getContact(id)
        val number = new NumberModel().getNumber(id)

        val response:JsValue = Json.toJson(Map("contacts" -> contact, "numbers" -> number))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        val updatedContact = payloadJson("contact")

        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt

        val status = new ContactModel().updateContact(id, updatedContact)

        val response:JsValue = Json.toJson(Map("contact" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }

    override def doDelete(req: HSReq, resp: HSResp) {
        val requestURI = req.getRequestURI()
        val id = requestURI.substring(requestURI.lastIndexOf("/") + 1).toInt

        val status = new ContactModel().deleteContact(id)

        val response:JsValue = Json.toJson(Map("contact" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }

}
