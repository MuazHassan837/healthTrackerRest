package ie.setu.domain.db
import org.jetbrains.exposed.sql.Table


/**
 * This class is used to define the table for the users
 */
object Users : Table("users") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val email = varchar("email", 255)
}


