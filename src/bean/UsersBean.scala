// package bean

// import java.sql.{Connection, DriverManager}
// import scala.collection.mutable.{ListBuffer, Map}
// import play.api.libs.json._

// class UsersBean extends DBManagerBean {

//     def signupUser(userData:Map[String, String]): Map[String, String] = {
//         val status = Map.empty[String, String]
//         var connection:Connection = null
//         try {
//             connection = DriverManager.getConnection(url, user, password)
//             val query = "INSERT INTO users(name, email, password, created_date) VALUES (?, ?, ?, ?)"
//             val statement = connection.prepareStatement(query)
//             statement.setString(1, userData("name"))
//             statement.setString(2, userData("email"))
//             statement.setString(3, userData("password"))
//             statement.setLong(4, userData("createdDate").toLong)
//             statement.executeUpdate()
//             status("status") = "success"
//         }
//         catch {
//             case exception:Exception => {
//                 println(exception.printStackTrace())
//                 status("status") = "failed"
//             }
//         }
//         finally {
//             if (connection != null) {
//                 connection.close()
//             }
//         }
//         status
//     }

//     def loginUser(email:String, password:String): Map[String, String] = {
//         val account = Map.empty[String, String]
//         var connection:Connection = null
//         try {
//             connection = DriverManager.getConnection(url, user, password)
//             val query = "SELECT * FROM users WHERE email = ? AND password = ?"
//             val statement = connection.prepareStatement(query)
//             statement.setString(1, email)
//             statement.setString(1, password)
//             val result = statement.executeQuery()
//             while (result.next()) {
//                 account("id") = result.getInt(1).toString()
//                 account("name") = result.getString(2)
//                 account("email") = result.getString(3)
//                 account("password") = result.getString(4)
//                 account("created_date") = result.getLong(5).toString()
//             }
//         }
//         catch {
//             case exception:Exception => {
//                 println(exception.printStackTrace())
//                 account("isError") = "true"
//             }
//         }
//         finally {
//             if (connection != null) {
//                 connection.close()
//             }
//         }
//         account
//     }

// }
