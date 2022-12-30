package ie.setu.repository

import ie.setu.domain.Fitness
import ie.setu.domain.db.Finesse
import ie.setu.domain.repository.FitnessDAO
import ie.setu.helpers.*
import ie.setu.helpers.populateFitnessTable
import ie.setu.helpers.populateUserForFitness
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


private val fitnessObj1 = fitnessObjs.get(0)
private val fitnessObj2 = fitnessObjs.get(1)


/**
 * This class is used to define the fitness unit tests
 */
class FitnessDAOTest : BasicDAOTest() {



    @Nested
    inner class createFitnessObjects{

        @Test
        fun `adding fitness objects into the table successfully`(){
            transaction {
                SchemaUtils.create(Finesse)
                val users = populateUserForFitness()
                val fitnessDAO = FitnessDAO()
                assertEquals(0,fitnessDAO.getAll().size)
                fitnessDAO.save(fitnessObj1)
                fitnessDAO.save(fitnessObj2)
                assertEquals(2,fitnessDAO.getAll().size)


                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `multiple fitness object added to the table can be fetched successfully`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)
                assertEquals(fitnessObj1,fitnessDAO.findByFitnessId(fitnessObj1.id))
                assertEquals(fitnessObj2,fitnessDAO.findByFitnessId(fitnessObj2.id))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }
    }

    @Nested
    inner class readFitnessObjects{

        @Test
        fun `getting all fitness objects from populated table returns all row`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `get fitness object by user id that has none, returns no record`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(0,fitnessDAO.findByUserId(Int.MIN_VALUE).size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `get fitness object by user id that exists, results in correct record`(){
            transaction {
                val users= populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(fitnessObj1,fitnessDAO.findByUserId(validID).get(0))
                assertEquals(fitnessObj2,fitnessDAO.findByUserId(2).get(0))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `get all fitness object over empty table returns non`(){
            transaction {
                SchemaUtils.create(Finesse)
                val fitnessDAO = FitnessDAO()
                assertEquals(0,fitnessDAO.getAll().size)
            }
        }

        @Test
        fun `get fitness object by id that has no records results in nothing returned`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(null,fitnessDAO.findByFitnessId(Int.MIN_VALUE))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `get fitness object by id that has record, results in hydration details returned`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(fitnessObj1,fitnessDAO.findByFitnessId(1))
                assertEquals(fitnessObj2,fitnessDAO.findByFitnessId(2))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

    }

    @Nested
    inner class updateFitnessObjects{
        @Test
        fun `updating existing fitness object results in successful update`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                val newObj = Fitness(id = 1, dayType = "ARM DAY", started = DateTime.now(), userId = validID)
                fitnessDAO.updateFitnessByID(1,newObj)
                assertEquals(newObj,fitnessDAO.findByFitnessId(newObj.id))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `updating invalid fitness object results in no update`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                val newObj = Fitness(id = 1, dayType = "ARM DAY", started = DateTime.now(), userId = validID)
                fitnessDAO.updateFitnessByID(10,newObj)
                assertEquals(null,fitnessDAO.findByFitnessId(10))

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }
    }

    @Nested
    inner class deleteFitnessObjects{

        @Test
        fun `delete a non existant fitness object results in no deletion`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)
                fitnessDAO.deleteFitnessByID(10)
                assertEquals(2,fitnessDAO.getAll().size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }

        @Test
        fun `delete a valid fitness object results in deletion`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)
                fitnessDAO.deleteFitnessByID(2)
                assertEquals(1,fitnessDAO.getAll().size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }


        @Test
        fun `delete a non existant users fitness history results in no deletion`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)
                fitnessDAO.deleteFitnessByUserId(404)
                assertEquals(2,fitnessDAO.getAll().size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }


        @Test
        fun `delete a existant users fitness history results in successful deletion`(){
            transaction {
                val users = populateUserForFitness()
                val fitnessDAO = populateFitnessTable()
                assertEquals(2,fitnessDAO.getAll().size)
                fitnessDAO.deleteFitnessByUserId(validID)
                assertEquals(1,fitnessDAO.getAll().size)

                deInitUsers(users)
                deInitFitnessTable(fitnessDAO)
            }
        }


    }
}