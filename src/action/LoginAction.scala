package action

import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.Map
import model.User
import workflow.LoginWorkflow

class LoginAction extends HelperAction {
    override def doGet(req: HSReq, resp: HSResp) {
        val email = req.getParameter("email")
        val password = req.getParameter("password")
        val user = User()
        user.email = email
        val workflow = new LoginWorkflow()
        val isAvailableUser = workflow.login(user, password)
        println("Is Available user : "+isAvailableUser)
        if (isAvailableUser) {
            setCookie(req, resp, "account", user.id)
            resp.sendRedirect("/contact-app/home/#/contacts")
        } else {
            resp.sendRedirect("/contact-app/home/#/login")
        }
    }
}
