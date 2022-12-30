package ie.setu.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.*
import junit.framework.TestCase.assertEquals
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val user1 = users.get(0)
private val user2 = users.get(1)
private val user3 = users.get(2)
private val user4 = users.get(3)

/**
 * This class is used to define the user unit tests
 */
class UserDAOTest : BasicDAOTest() {



    @Nested
    inner class createAndTest {
        @Test
        fun `multiple users added to table can be retrieved successfully`() {
            transaction {

                SchemaUtils.create(Users)
                val userDAO = populateThreeUserTable()

                assertEquals(3, userDAO.getAll().size)
                assertEquals(user1, userDAO.findById(user1.id))
                assertEquals(user2, userDAO.findById(user2.id))
                assertEquals(user3, userDAO.findById(user3.id))

                deInitUsers(userDAO)
            }
        }
        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {

                SchemaUtils.create(Users)
                val userDAO = populateThreeUserTable()
                assertEquals(3, userDAO.getAll().size)

                deInitUsers(userDAO)
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                SchemaUtils.create(Users)
                val userDAO = UserDAO()
                assertEquals(0, userDAO.getAll().size)
            }
        }
    }


    @Nested
    inner class findAndTest {
        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                SchemaUtils.create(Users)
                val userDAO = populateThreeUserTable()
                assertEquals(null, userDAO.findById(4))

                deInitUsers(userDAO)
            }
        }
        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                SchemaUtils.create(Users)
                val userDAO = populateFourUserTable()
                assertEquals(user4, userDAO.findById(4))

                deInitUsers(userDAO)
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {
                val userDAO = populateThreeUserTable()
                assertEquals(null, userDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get user by email that exists, results in correct user returned`() {
            transaction {
                val userDAO = populateThreeUserTable()
                assertEquals(user2, userDAO.findByEmail(user2.email))

                deInitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class deleteAndTest {
        @Test
        fun `deleting a non-existant user in table results in no deletion`() {
            transaction {
                val userDAO = populateThreeUserTable()
                assertEquals(3, userDAO.getAll().size)
                userDAO.delete(4)
                assertEquals(3, userDAO.getAll().size)

                deInitUsers(userDAO)
            }
        }
        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {
                val userDAO = populateThreeUserTable()
                assertEquals(3, userDAO.getAll().size)
                userDAO.delete(user3.id)
                assertEquals(2, userDAO.getAll().size)

                deInitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class updateAndTest {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {
                val userDAO = populateThreeUserTable()
                val user3Updated = User(3, "new username", "new@email.ie")
                userDAO.update(user3.id, user3Updated)
                assertEquals(user3Updated, userDAO.findById(3))

                deInitUsers(userDAO)
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {
                val userDAO = populateThreeUserTable()
                val user4Updated = User(4, "new username", "new@email.ie")
                userDAO.update(4, user4Updated)
                assertEquals(null, userDAO.findById(4))
                assertEquals(3, userDAO.getAll().size)

                deInitUsers(userDAO)
            }
        }
    }

}