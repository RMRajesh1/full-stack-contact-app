package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp, Cookie}
import play.api.libs.json.{JsValue, Json}
import java.util.{UUID, Date}

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

    def setCookie(req: HSReq, resp: HSResp, name: String, value: String, hours: Int = 24 ) {
        val cookieAge = 100 * 60 * 60 * hours
        val cookie = new Cookie(name, value)
        cookie.setMaxAge(cookieAge)
        resp.addCookie(cookie)
    }

    def generateRandomId(): String = {
        val randomId = UUID.randomUUID()
        randomId.toString()
    }

    def getDate(): Long = {
        val millis = new Date().getTime
        millis
    }

}
