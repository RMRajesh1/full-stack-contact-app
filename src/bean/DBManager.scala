package bean

import java.sql.{Connection, DriverManager}

class DBManager {
    val url = "jdbc:postgresql://localhost:5432/contact_application"
    val dbUser = "postgres"
    val password = ""
    Class.forName("org.postgresql.Driver")

    def closeConnection(connection: Connection) {
        if (connection != null) {
            connection.close()
        }
    }
}
