package ie.setu.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.repository.ActivityDAO
import ie.setu.helpers.activities
import ie.setu.helpers.populateActivityTable
import ie.setu.helpers.populateThreeUserTable
import ie.setu.helpers.validID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import junit.framework.TestCase
import org.jetbrains.exposed.sql.transactions.transaction
import junit.framework.TestCase.assertEquals
import org.joda.time.DateTime

private val activity1 = activities.get(0)
private val activity2 = activities.get(1)
private val activity3 = activities.get(2)

class ActivityDAOTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateActivities {

        @Test
        fun `multiple activities added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
                kotlin.test.assertEquals(activity1, activityDAO.findByActivityId(activity1.id))
                kotlin.test.assertEquals(activity2, activityDAO.findByActivityId(activity2.id))
                kotlin.test.assertEquals(activity3, activityDAO.findByActivityId(activity3.id))
            }
        }
    }

    @Nested
    inner class ReadActivities {

        @Test
        fun `getting all activites from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activity by user id that has no activities, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(0, activityDAO.findByUserId(3).size)
            }
        }

        @Test
        fun `get activity by user id that exists, results in a correct activitie(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(activity1, activityDAO.findByUserId(validID).get(0))
                kotlin.test.assertEquals(activity2, activityDAO.findByUserId(validID).get(1))
                kotlin.test.assertEquals(activity3, activityDAO.findByUserId(validID).get(2))
            }
        }

        @Test
        fun `get all activities over empty table returns none`() {
            transaction {

                //Arrange - create and setup activityDAO object
                SchemaUtils.create(Activities)
                val activityDAO = ActivityDAO()

                //Act & Assert
                kotlin.test.assertEquals(0, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activity by activity id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(null, activityDAO.findByActivityId(4))
            }
        }

        @Test
        fun `get activity by activity id that exists, results in a correct activity returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()
                //Act & Assert
                kotlin.test.assertEquals(activity1, activityDAO.findByActivityId(1))
                kotlin.test.assertEquals(activity3, activityDAO.findByActivityId(3))
            }
        }
    }

    @Nested
    inner class UpdateActivities {

        @Test
        fun `updating existing activity in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                val activity3updated = Activity(id = 3, description = "Cardio", duration = 42.0,
                    calories = 220, started = DateTime.now(), userId = 2)
                activityDAO.updateActivityByID(activity3updated.id, activity3updated)
                kotlin.test.assertEquals(activity3updated, activityDAO.findByActivityId(3))
            }
        }

        @Test
        fun `updating non-existant activity in table results in no updates`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                val activity4updated = Activity(id = 4, description = "Cardio", duration = 42.0,
                    calories = 220, started = DateTime.now(), userId = 2)
                activityDAO.updateActivityByID(4, activity4updated)
                kotlin.test.assertEquals(null, activityDAO.findByActivityId(4))
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteActivities {

        @Test
        fun `deleting a non-existant activity (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivityByID(4)
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing activity (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivityByID(activity3.id)
                kotlin.test.assertEquals(2, activityDAO.getAll().size)
            }
        }


        @Test
        fun `deleting activities when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivitiesOfUserID(3)
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `deleting activities when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateThreeUserTable()
                val activityDAO = populateActivityTable()

                //Act & Assert
                kotlin.test.assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivitiesOfUserID(validID)
                kotlin.test.assertEquals(0, activityDAO.getAll().size)
            }
        }
    }
}