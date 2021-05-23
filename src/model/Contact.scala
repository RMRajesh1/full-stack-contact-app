package model

case class Contact (
  var id: String = null,
  var name: String = null,
  var image: String = null,
  var email: String = null,
  var description: String = null,
  var date: Long = 0,
  var user: String = null
)
