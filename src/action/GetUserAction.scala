package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.{ListBuffer, Map}
import model.User
import workflow.UserWorkflow

class GetUserAction extends HelperAction {

    override def doGet(req: HSReq, resp: HSResp) {
        val id = req.getParameter("account")
        val user = User(id)
        val workflow = new UserWorkflow()
        workflow.getUser(user)
        val response = Json.toJson(Map("user" -> getValuesAsMap(user)))
        sendJsonResponse(req, resp, response)
    }

    def getValuesAsMap(user: User): Map[String, String] = {
        Map(
            "id" -> user.id,
            "name" -> user.name,
            "email" -> user.email,
            "createdDate" -> user.createdDate.toString
        )
    }

}
