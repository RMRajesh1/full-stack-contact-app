import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.immutable.Map

class EditNumberAction extends HttpServlet {
    override def doPut(req: HSReq, resp: HSResp) {
        val reader = req.getReader()
        val jsonMap:JsValue = Json.parse(reader.readLine())
        println(jsonMap)
        println(jsonMap("number"))

        val json:JsValue = Json.toJson(Map("data" -> "numbers"))

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        val out = resp.getWriter()
        out.println(Json.stringify(json))
    }
}