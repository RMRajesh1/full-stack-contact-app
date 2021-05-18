import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.immutable.Map

class FetchContactList extends HttpServlet {
    override def doGet(req: HSReq, resp: HSResp) {
        val db = new DBManager()
        val json:JsValue = db.getContactList()

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        
        val out = resp.getWriter()
        out.println(Json.stringify(json))
    }
}
