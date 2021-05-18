import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.immutable.Map

class EditContactData extends HttpServlet {
    override def doGet(req: HSReq, resp: HSResp) {
        val db = new DBManager()
        val requestURI = req.getRequestURI()
        val contactId = requestURI.substring(requestURI.lastIndexOf("/") + 1)
        val json:JsValue = db.getContact(contactId)

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        val out = resp.getWriter()
        out.println(Json.stringify(json))
    }

    override def doPut(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val jsonMap:JsValue = Json.parse(reader.readLine())
        println(jsonMap)
        println(jsonMap("contact"))
        val json:JsValue = Json.toJson(Map("data" -> "contact"))

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        val out = resp.getWriter()
        out.println(Json.stringify(json))
    }
}
