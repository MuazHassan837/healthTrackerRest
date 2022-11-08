package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Moods : Table("moods") {
    val id = integer("id").autoIncrement().primaryKey()
    val mood = varchar("mood", 100)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}
