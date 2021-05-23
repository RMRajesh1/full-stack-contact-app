package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.Map
import model.User
import workflow.Signup

class SignupAction extends HelperAction {
    override def doPost(req: HSReq, resp: HSResp) {
        val name = req.getParameter("username")
        val email = req.getParameter("email")
        val password = req.getParameter("password")
        val id = generateRandomId
        val createdDate = getDate()
        val user = User(id, name, email, password, createdDate)
        val workflow = new Signup()
        workflow.signup(user)
        setCookie(req, resp, "account", user.id)
        resp.sendRedirect("/contact-app/home/#/contacts")
    }
}
