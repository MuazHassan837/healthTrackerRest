package ie.setu.domain.repository

import ie.setu.domain.Mood
import ie.setu.domain.db.Moods
import ie.setu.utils.mapToMood
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * This class is used to define the repository actions for the moods
 */
class MoodDAO {

    fun getAll() : ArrayList<Mood> {
        val moodObj : ArrayList<Mood> = arrayListOf()

        transaction {
            Moods.selectAll().map {
                moodObj.add(mapToMood(it))
            }
        }
        return moodObj
    }

    fun save(ComingObj: Mood){
        transaction {
            Moods.insert {
                it[id] = ComingObj.id
                it[mood] = ComingObj.mood
                it[userId] = ComingObj.userId
                it[started] = ComingObj.started
            }
        }
    }

    fun updateMoodById(comingID: Int, comingObj: Mood): Int {
        return transaction {
            Moods.update({Moods.id eq comingID}){
                it[id] = comingObj.id
                it[mood] = comingObj.mood
                it[userId] = comingObj.userId
                it[started] = comingObj.started
            }
        }
    }

    fun delMoodById(comingId: Int): Int {
        return transaction {
            Moods.deleteWhere {
                Moods.id eq comingId
            }
        }
    }

    fun delMoodByUserId(comingId: Int): Int{
        return transaction {
            Moods.deleteWhere {
                Moods.userId eq comingId
            }
        }
    }


    fun findById(comingId: Int): Mood? {
        return transaction {
            Moods.select(){Moods.id eq comingId}
                .map { mapToMood(it) }
                .firstOrNull()
        }
    }

    fun findByUserId(comingId: Int): List<Mood>{
        return transaction {
            Moods.select{Moods.userId eq comingId}
                .map { mapToMood(it) }
        }
    }

}