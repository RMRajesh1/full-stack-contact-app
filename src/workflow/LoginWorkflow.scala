package workflow

import model.User
import bean.UsersBean

class LoginWorkflow extends HelperWorkflow {
    def login(user: User, password: String): Boolean = {
        val db = new UsersBean()
        db.getUser(user)
        if (user.password == password) {
            return true
        }
        false
    }
}
