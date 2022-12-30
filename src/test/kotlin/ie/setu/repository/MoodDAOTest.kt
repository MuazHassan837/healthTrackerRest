package ie.setu.repository

import ie.setu.domain.Mood
import ie.setu.domain.db.Moods
import ie.setu.domain.repository.MoodDAO
import ie.setu.helpers.*
import ie.setu.helpers.populateMoodTable
import ie.setu.helpers.populateUserForMood
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


private val moodObj1 = moodObjs.get(0)
private val moodObj2 = moodObjs.get(1)

/**
 * This class is used to define the mood unit tests
 */
class MoodDAOTest : BasicDAOTest(){


    @Nested
    inner class createMoodObjects{
        @Test
        fun `adding mood objects into the table successfully`(){
            transaction {
                SchemaUtils.create(Moods)
                val users = populateUserForMood()
                val moodDAO = MoodDAO()
                assertEquals(0,moodDAO.getAll().size)
                moodDAO.save(moodObj1)
                moodDAO.save(moodObj2)
                assertEquals(2,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `multiple mood object added to the table can be fetched successfully`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)
                assertEquals(moodObj1,moodDAO.findById(moodObj1.id))
                assertEquals(moodObj2,moodDAO.findById(moodObj2.id))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }
    }


    @Nested
    inner class readMoodObjects {
        @Test
        fun `getting all mood objects from populated table returns all row`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }


        @Test
        fun `get mood object by user id that has none, returns no record`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(0,moodDAO.findByUserId(Int.MIN_VALUE).size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `get mood object by user id that exists, results in correct record`(){
            transaction {
                val users  = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(moodObj1,moodDAO.findByUserId(validID).get(0))
                assertEquals(moodObj2,moodDAO.findByUserId(2).get(0))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `get all mood object over empty table returns non`(){
            transaction {
                SchemaUtils.create(Moods)
                val moodDAO = MoodDAO()
                assertEquals(0,moodDAO.getAll().size)

            }
        }

        @Test
        fun `get mood object by id that has no records results in nothing returned`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(null,moodDAO.findById(Int.MIN_VALUE))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `get mood object by id that has record, results in correct mood returned`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(moodObj1,moodDAO.findById(1))
                assertEquals(moodObj2,moodDAO.findById(2))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }
    }


    @Nested
    inner class updateMood {

        @Test
        fun `updating non-existing mood object results in no update`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                val newObj = Mood(id = 1, mood = "uncertain", userId = validID, DateTime.now())
                moodDAO.updateMoodById(10,newObj)
                assertEquals(null,moodDAO.findById(10))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `updating existing mood object results in successful update`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                val newObj = Mood(id = 1, mood = "uncertain", userId = validID,DateTime.now())
                moodDAO.updateMoodById(1,newObj)
                assertEquals(newObj,moodDAO.findById(newObj.id))

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

    }



    @Nested
    inner class deleteMood {
        @Test
        fun `delete a non existant mood object results in no deletion`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)
                moodDAO.delMoodById(404)
                assertEquals(2,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }


        @Test
        fun `delete a valid mood object results in deletion`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)
                moodDAO.delMoodById(2)
                assertEquals(1,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }


        @Test
        fun `delete a non existant users mood history results in no deletion`(){
            transaction {
                val users  = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)
                moodDAO.delMoodByUserId(404)
                assertEquals(2,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }

        @Test
        fun `delete a existant users mood history results in successful deletion`(){
            transaction {
                val users = populateUserForMood()
                val moodDAO = populateMoodTable()
                assertEquals(2,moodDAO.getAll().size)
                moodDAO.delMoodByUserId(validID)
                assertEquals(1,moodDAO.getAll().size)

                deInitUsers(users)
                deInitMoodTable(moodDAO)
            }
        }
    }


}