package workflow

import model.User
import bean.UsersBean

class FilterWorkflow extends HelperWorkflow {
    def getUser(user: User) {
        val db = new UsersBean()
        db.getUser(user)
    }
}