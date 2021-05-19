package model

import scala.collection.mutable.{ListBuffer, Map}
import play.api.libs.json._
import workflow._

class ContactModel {

  def getContacts(): ListBuffer[Map[String, String]] = {
    val workflow = new ContactWorkflow()
    workflow.getAllContacts()
  }

  def getContact(id:Int): ListBuffer[Map[String, String]] = {
    val workflow = new ContactWorkflow()
    workflow.getContact(id)
  }

  def updateContact(id:Int, updatedContact:JsValue): Map[String, String] = {
    val workflow = new ContactWorkflow()
    workflow.updateContact(id, updatedContact)
  }

  def createContact(contact:JsValue): Map[String, String] = {
    val workflow = new ContactWorkflow()
    workflow.createContact(contact)
  }

  def deleteContact(id:Int): Map[String, String] = {
    val workflow = new ContactWorkflow()
    workflow.deleteContact(id)
  }

}