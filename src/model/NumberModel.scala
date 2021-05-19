package model

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import workflow._

class NumberModel {

    def getNumbers(): ListBuffer[Map[String, String]] = {
        val workflow = new NumberWorkflow()
        workflow.getAllNumbers()
    }

    def getNumber(id:Int): ListBuffer[Map[String, String]] = {
        val workflow = new NumberWorkflow()
        workflow.getNumber(id)
    }

    def updateNumber(id:Int, updatedNumber:JsValue): Map[String, String] = {
        val workflow = new NumberWorkflow()
        workflow.updateNumber(id, updatedNumber)
    }

    def createNumber(number:JsValue): Map[String, String] = {
        val workflow = new NumberWorkflow()
        workflow.createNumber(number)
    }

    def deleteNumber(id:Int): Map[String, String] = {
        val workflow = new NumberWorkflow()
        workflow.deleteNumber(id)
    }

}