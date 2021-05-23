package model

case class User (
    var id: String = null, 
    var name: String = null, 
    var email: String = null, 
    var password: String = null, 
    var createdDate: Long = 0
)
