package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.{ListBuffer, Map}
import model.User
import workflow.UserWorkflow

class GetUserAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val id = req.getParameter("account")
        val user = User(id, null, null, null, 0)
        val workflow = new UserWorkflow()
        workflow.getUser(user)
        val response = Json.toJson(Map("user" -> getValuesAsMap(user)))
        sendJsonResponse(req, resp, response)
    }

    def getValuesAsMap(user: User, map: Map[String, String] = Map.empty): Map[String, String] = {
        map("id") = user.id
        map("name") = user.name
        map("email") = user.email
        map("createdDate") = user.createdDate.toString()
        map
    }

}
