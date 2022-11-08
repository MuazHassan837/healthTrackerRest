package ie.setu.domain.db


import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object InTakes : Table("intakes") {
    val id = InTakes.integer("id").autoIncrement().primaryKey()
    val amountltr = double("amountltr")
    val substance = varchar("substance", 100)
    val userId = InTakes.integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}





