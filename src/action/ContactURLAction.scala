package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}
import model._

class ContactURLAction extends HttpServlet {
    override def doGet(req: HSReq, resp: HSResp) {
        val contacts = new ContactModel().getContacts()
        val numbers = new NumberModel().getNumbers()

        val response:JsValue = Json.toJson(Map("contacts" -> contacts, "numbers" -> numbers))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }

    override def doPost(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        val newContact = payloadJson("contact")

        val status = new ContactModel().createContact(newContact)

        val response:JsValue = Json.toJson(Map("contact" -> status))
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }
}