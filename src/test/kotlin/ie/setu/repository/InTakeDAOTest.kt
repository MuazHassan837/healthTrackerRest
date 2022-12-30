package ie.setu.repository

import ie.setu.domain.InTake
import ie.setu.domain.db.InTakes
import ie.setu.domain.repository.InTakeDAO
import ie.setu.helpers.*
import ie.setu.helpers.favUserTable
import ie.setu.helpers.populateInTakeTable
import ie.setu.helpers.populateThreeUserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private val hydration1 = intakes.get(0)
private val hydration2  = intakes.get(1)
private val userImp = users[0]

/**
 * This class is used to define the hydration unit tests
 */
class InTakeDAOTest : BasicDAOTest() {

    @Nested
    inner class CreateHydrationHist {

        @Test
        fun `adding hydration objects into the table successfully`(){
            transaction {
                SchemaUtils.create(InTakes)
                val user = favUserTable()
                var intakeDAO = InTakeDAO()
                assertEquals(0,intakeDAO.getAll().size)
                intakeDAO.save(hydration1)
                intakeDAO.save(hydration2)
                assertEquals(2,intakeDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(intakeDAO)
            }
        }


        @Test
        fun `multiple hydration objects added to table and fetched successfully`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()

                assertEquals(2,hydrationDAO.getAll().size)
                assertEquals(hydration1,hydrationDAO.findByInTakeId(hydration1.id))
                assertEquals(hydration2,hydrationDAO.findByInTakeId(hydration2.id))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)

            }
        }
    }

    @Nested
    inner class ReadHydrationHist(){
        @Test
        fun `getting all hydration objects from populated returns all row`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(2,hydrationDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `get intake object by user id that has none, returns no record`(){
            transaction {
                val user = populateThreeUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(0,hydrationDAO.findByUserID(Int.MIN_VALUE).size)

                deInitUsers(user)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `get intake object by user id that exists, results in correct record`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(hydration1,hydrationDAO.findByUserID(validID).get(0))
                assertEquals(hydration2,hydrationDAO.findByUserID(validID).get(1))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `get all intake object over empty table returns non`(){
            transaction {
                favUserTable()
                SchemaUtils.create(InTakes)
                val hydrationDAO = InTakeDAO()
                assertEquals(0,hydrationDAO.getAll().size)
            }
        }

        @Test
        fun `get intake object by intake id that has no records results in nothing returned`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(null,hydrationDAO.findByInTakeId(Int.MIN_VALUE))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `get intake object by intake id that has record, results in hydration details returned`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(hydration1,hydrationDAO.findByInTakeId(1))
                assertEquals(hydration2,hydrationDAO.findByInTakeId(2))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

    }

    @Nested
    inner class UpdateHydrationHist(){
        @Test
        fun `updating existing hydration object results in successful update`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                val newObj = InTake(id = 1, amountltr = 3.2, substance = "Apple Juice", userId = validID, DateTime.now())
                hydrationDAO.updateInTakeById(1,newObj)
                assertEquals(newObj,hydrationDAO.findByInTakeId(newObj.id))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }


        @Test
        fun `updating invalid hydration object results in no update`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                val newObj = InTake(id = 32, amountltr = 3.2, substance = "Apple Juice", userId = validID, DateTime.now())
                hydrationDAO.updateInTakeById(32,newObj)
                assertEquals(null,hydrationDAO.findByInTakeId(32))

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }
    }


    @Nested
    inner class DeleteHydrationHist {

        @Test
        fun `delete a non existant hydration object results in no deletion`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(2,hydrationDAO.getAll().size)
                hydrationDAO.deleteInTakeByID(324)
                assertEquals(2,hydrationDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `delete a existant hydration object results in deletion`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(2,hydrationDAO.getAll().size)
                hydrationDAO.deleteInTakeByID(1)
                assertEquals(1,hydrationDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `delete a non existant users hydration history results in no deletion`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(2,hydrationDAO.getAll().size)
                hydrationDAO.deleteIntakesOfUserID(400)
                assertEquals(2,hydrationDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }

        @Test
        fun `delete a existant users hydration history results in successful deletion`(){
            transaction {
                val user = favUserTable()
                val hydrationDAO = populateInTakeTable()
                assertEquals(2,hydrationDAO.getAll().size)
                hydrationDAO.deleteIntakesOfUserID(validID)
                assertEquals(0,hydrationDAO.getAll().size)

                user.delete(userImp.id)
                deInitInTakeObj(hydrationDAO)
            }
        }
    }

}