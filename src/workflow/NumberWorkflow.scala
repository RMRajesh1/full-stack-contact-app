package workflow

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import bean._

class NumberWorkflow {

    def getAllNumbers(): ListBuffer[Map[String, String]] = {
        val db = new DBManagerBean()
        db.getAllNumbers()
    }

    def getNumber(id:Int): ListBuffer[Map[String, String]] = {
        val db = new DBManagerBean()
        db.getNumber(id)
    }

    def updateNumber(id:Int, updatedNumber:JsValue): Map[String, String] = {
        val db = new DBManagerBean()
        db.updateNumber(id, updatedNumber)
    }

    def createNumber(number:JsValue): Map[String, String] = {
        val db = new DBManagerBean()
        db.createNumber(number)
    }

    def deleteNumber(id:Int): Map[String, String] = {
        val db = new DBManagerBean()
        db.deleteNumber(id)
    }

}