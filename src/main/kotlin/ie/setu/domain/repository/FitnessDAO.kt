package ie.setu.domain.repository

import ie.setu.domain.Fitness
import ie.setu.domain.db.Finesse
import ie.setu.utils.mapToFitness
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * This class is used to define the repository actions for the fitness
 */
class FitnessDAO {


    fun getAll() : ArrayList<Fitness> {
        val fitnessObjs : ArrayList<Fitness> = arrayListOf()
        transaction {
            Finesse.selectAll().map {
                fitnessObjs.add(mapToFitness(it))
            }
        }
        return fitnessObjs
    }

    fun save(comingObj : Fitness){
        transaction {
            Finesse.insert {
                it[id] = comingObj.id
                it[dayType] = comingObj.dayType
                it[started] = comingObj.started
                it[userId] = comingObj.userId
            }
        }
    }



    fun updateFitnessByID(comingId: Int, comingObj: Fitness): Int{
        return transaction {
            Finesse.update({Finesse.id eq comingId}){
                it[id] = comingObj.id
                it[dayType] = comingObj.dayType
                it[started] = comingObj.started
                it[userId] = comingObj.userId
            }
        }
    }

    fun deleteFitnessByID(comingId: Int) :Int {
        return transaction {
            Finesse.deleteWhere{
                Finesse.id eq comingId
            }
        }
    }

    fun deleteFitnessByUserId(comingId: Int): Int{
        return transaction {
            Finesse.deleteWhere {
                Finesse.userId eq comingId
            }
        }
    }

    fun findByFitnessId(comingId: Int): Fitness?{
        return transaction {
            Finesse.select(){Finesse.id eq comingId}
                .map { mapToFitness(it) }
                .firstOrNull()
        }
    }

    fun findByUserId(userId: Int): List<Fitness>{
        return transaction {
            Finesse.select{ Finesse.userId eq userId}
                .map { mapToFitness(it)}
        }
    }

}