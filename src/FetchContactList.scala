import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.immutable.Map

class FetchContactList extends HttpServlet {
    override def doGet(req: HSReq, resp: HSResp) {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        val db = new DBManager()
        val json:JsValue = db.getContactList()
        println(json)
        out.println(Json.stringify(json))
    }
}