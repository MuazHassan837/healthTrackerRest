package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Finesse  : Table("finesse"){
    val id = integer("id").autoIncrement().primaryKey()
    val dayType = varchar("daytype", 100)
    val started = datetime("started")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}
