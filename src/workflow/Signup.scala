package workflow

import model.User
import bean.UsersBean

class Signup extends HelperWorkflow {
    def signup(user: User) {
        val db = new UsersBean()
        db.createUser(user)
    }
}
