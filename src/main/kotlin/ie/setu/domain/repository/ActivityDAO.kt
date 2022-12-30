package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * This class is used to define the repository actions for the activities
 */
class ActivityDAO {

    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    fun findByUserId(userId: Int): List<Activity>{
        return transaction {
            Activities
                .select {Activities.userId eq userId}
                .map {mapToActivity(it)}
        }
    }

    fun save(activity: Activity) {
        transaction {
            Activities.insert {
                it[id] = activity.id
                it[description] = activity.description
                it[duration] = activity.duration
                it[started] = activity.started
                it[calories] = activity.calories
                it[userId] = activity.userId
            }
        }
    }

    fun deleteActivitiesOfUserID(comingID: Int):Int {
        return transaction {
            Activities.deleteWhere {
                Activities.userId eq comingID
            }
        }
    }
    fun deleteActivityByID(comingID: Int):Int {
        return transaction {
            Activities.deleteWhere {
                Activities.id eq comingID
            }
        }
    }

    fun updateActivityByID(comingID: Int, comingAct: Activity): Int{
        return transaction {
            Activities.update ({
                Activities.id eq comingID}) {
                it[description] = comingAct.description
                it[duration] = comingAct.duration
                it[calories] = comingAct.calories
                it[started] = comingAct.started
                it[userId] = comingAct.userId
            }
        }
    }


    fun findByActivityId(actID: Int): Activity?{
        return transaction {
            Activities
                .select() { Activities.id eq actID}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }


}