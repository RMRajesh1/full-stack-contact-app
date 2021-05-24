package workflow

import scala.collection.mutable.{ListBuffer, Map}
import model.Number
import model.User
import bean.NumberBean

class NumberWorkflow extends HelperWorkflow {

    def createNumber(number: Number) {
        number.id = generateRandomId()
        val db = new NumberBean()
        db.createNumber(number)
    }

    def getAllNumbers(userId: String, contactId: String = null): ListBuffer[Number] = {
        val db = new NumberBean()
        val numbers = db.getAllNumbers(userId, contactId)
        numbers
    }

    def updateNumber(number: Number) {
        val db = new NumberBean()
        db.updateNumber(number)
    }

    def deleteNumber(number: Number): Boolean = {
        val db = new NumberBean()
        db.deleteNumber(number)
    }

}