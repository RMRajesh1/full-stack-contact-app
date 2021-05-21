package workflow

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import model._
import bean._

class NumberWorkflow extends HelperWorkflow {

    def createNumber(number:JsValue): Map[String, String] = {
        val numberModel = new NumberModel()
        setValues(numberModel, number, true)
        numberModel.id = generateRandomId()
        val db = new NumberBean()
        val isSuccess = db.createNumber(numberModel)
        var response = Map.empty[String, String]
        if (isSuccess) {
            getValuesAsMap(numberModel, response)
        }   else {
            response("status") = "failed"
        }
        response
    }

    def getAllNumbers(contactId: String = null): ListBuffer[Map[String, String]] = {
        val db = new NumberBean()
        val numberList = db.getAllNumbers(contactId)
        val numbers = ListBuffer[Map[String, String]]()
        numberList.foreach {
            case item => {
                numbers += getValuesAsMap(item)
            }
        }
        numbers
    }

    def getNumber(id: String): Map[String, String] = {
        val db = new NumberBean()
        val number = new NumberModel()
        db.getNumber(id, number)
        getValuesAsMap(number)
    }

    def updateNumber(id:String, updatedNumber:JsValue): Map[String, String] = {
        val number = new NumberModel()
        number.id = id
        setValues(number, updatedNumber)
        var response = Map.empty[String, String]
        val db = new NumberBean()
        val isSuccess = db.updateNumber(number)
        if (isSuccess) {
            getValuesAsMap(number, response)
        } else {
            response("status") = "failed"
        }
        response
    }

    def deleteNumber(id:String): Map[String, String] = {
        val response = Map.empty[String, String]
        val db = new NumberBean()
        val isSuccess = db.deleteNumber(id)
        if (isSuccess) {
            response("status") = "success"
        } else {
            response("status") = "failed"
        }
        response
    }

    def setValues(number: NumberModel, data: JsValue, isNewEntry: Boolean = false) {
        if (!isNewEntry) {
            number.contact = data("contact").as[String]
        }
        number.number = data("number").as[String]
        number.numberType = data("type").as[Int].toString
    }

    def getValuesAsMap(number: NumberModel, map: Map[String, String] = Map.empty[String, String]): Map[String, String] = {
        map("id") = number.id
        map("number") = number.number
        map("type") = number.numberType
        map("contact") = number.contact
        map
    }

}