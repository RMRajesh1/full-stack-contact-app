package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json._
import scala.collection.mutable.{ListBuffer, Map}

class HelperAction extends HttpServlet {

    def sendJsonResponse(req: HSReq, resp: HSResp, response: JsValue) {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        val out = resp.getWriter()
        out.println(Json.stringify(response))
    }

    def getEndUrlPattern(req: HSReq, resp: HSResp): String = {
        val requestURI = req.getRequestURI()
        val urlPattern = requestURI.substring(requestURI.lastIndexOf("/") + 1)
        urlPattern
    }

    def payloadData(req: HSReq, resp: HSResp, name: String): JsValue = {
        val reader = req.getReader()
        val payloadJson:JsValue = Json.parse(reader.readLine())
        payloadJson(name)
    }

}
