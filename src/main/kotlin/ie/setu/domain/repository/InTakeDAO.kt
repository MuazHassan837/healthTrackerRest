package ie.setu.domain.repository

import ie.setu.domain.InTake
import ie.setu.domain.db.InTakes
import ie.setu.utils.mapToInTake
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * This class is used to define the repository actions for the hydration
 */
class InTakeDAO {

    fun getAll() : ArrayList<InTake> {
        val intakeList :  ArrayList<InTake> = arrayListOf()
        transaction {
            InTakes.selectAll().map {
                intakeList.add(mapToInTake(it))
            }
        }
        return intakeList
    }

    fun findByUserID(userId: Int): List<InTake>{
        return transaction {
            InTakes
                .select { InTakes.userId eq userId}
                .map { mapToInTake(it) }
        }
    }

    fun save(intake : InTake){
        transaction {
            InTakes.insert {
                it[id] = intake.id
                it[amountltr] = intake.amountltr
                it[substance] = intake.substance
                it[userId] = intake.userId
                it[started] = intake.started
            }
        }
    }

    fun deleteIntakesOfUserID(comingID: Int): Int {
        return transaction {
            InTakes.deleteWhere {
                InTakes.userId eq comingID
            }
        }
    }

    fun deleteInTakeByID(comingID: Int): Int {
        return transaction {
            InTakes.deleteWhere {
                InTakes.id eq comingID
            }
        }
    }

    fun updateInTakeById(comingID: Int, newObj: InTake): Int {
        return transaction {
            InTakes.update({ InTakes.id eq comingID}){
                it[amountltr] = newObj.amountltr
                it[substance] = newObj.substance
                it[userId]  = newObj.userId
                it[started] = newObj.started
            }
        }
    }

    fun findByInTakeId(IntId: Int): InTake?{
        return transaction {
            InTakes
                .select() { InTakes.id eq IntId}
                .map{ mapToInTake(it) }
                .firstOrNull()
        }
    }
}