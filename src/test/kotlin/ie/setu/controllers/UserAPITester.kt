package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import junit.framework.TestCase
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * This class is used to define the user API tests
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class UserAPITester {

    val db = DbConfig().getDbConnection()
    val app = ServerContainer.instance
    val origin = "http://localhost:" + app.port()

    fun addUser (id : Int, name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users")
            .body("{\"id\":\"$id\",\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }
    fun deleteUser (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$id").asString()
    }

    fun retrieveUserByEmail(email : String) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/email/${email}").asString()
    }

    //helper function to retrieve a test user from the database by id
    fun retrieveUserById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${id}").asString()
    }

    fun updateUser (id: Int, name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/$id")
            .body("{\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }


    @Nested
    inner class ReadUsers {
        @Test
        fun `get all users from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/api/users/").asString()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<User> = jsonToObject(response.body.toString())
                TestCase.assertEquals(0, retrievedUsers.size)
            }
            else {
                TestCase.assertEquals(404, response.status)
            }
        }

        @Test
        fun `get user by id when user does not exist returns 404 response`() {
            val id = Integer.MIN_VALUE
            val retrieveResponse = Unirest.get(origin + "/api/users/${id}").asString()
            TestCase.assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `get user by email when user does not exist returns 404 response`() {
            val retrieveResponse = Unirest.get(origin + "/api/users/email/$nonExistingEmail").asString()
            TestCase.assertEquals(404, retrieveResponse.status)
        }



        @Test
        fun `getting a user by id when id exists, returns a 200 response`() {
            val addResponse = addUser(validID, validName, validEmail)
            val addedUser : User = jsonToObject(addResponse.body.toString())
            val retrieveResponse = retrieveUserById(addedUser.id)
            TestCase.assertEquals(200, retrieveResponse.status)
            deleteUser(addedUser.id)
        }

        @Test
        fun `getting a user by email when email exists, returns a 200 response`() {
            addUser(validID, validName, validEmail)
            val retrieveResponse = retrieveUserByEmail(validEmail)
            TestCase.assertEquals(200, retrieveResponse.status)
            val retrievedUser : User = jsonToObject(retrieveResponse.body.toString())
            deleteUser(retrievedUser.id)
        }
    }




    @Nested
    inner class CreateUsers {
        @Test
        fun `add a user with correct details returns a 201 response`() {

            val addResponse = addUser(validID, validName, validEmail)
            TestCase.assertEquals(201, addResponse.status)
            val retrieveResponse= retrieveUserByEmail(validEmail)
            TestCase.assertEquals(200, retrieveResponse.status)
            val retrievedUser : User = jsonToObject(addResponse.body.toString())
            TestCase.assertEquals(validEmail, retrievedUser.email)
            TestCase.assertEquals(validName, retrievedUser.name)
            val deleteResponse = deleteUser(retrievedUser.id)
            TestCase.assertEquals(204, deleteResponse.status)
        }
    }

    @Nested
    inner class UpdateUsers {
        @Test
        fun `updating a user when it exists, returns a 204 response`() {
            val addResponse = addUser(validID, validName, validEmail)
            val addedUser : User = jsonToObject(addResponse.body.toString())
            TestCase.assertEquals(204, updateUser(addedUser.id, updatedName, updatedEmail).status)
            val updatedUserResponse = retrieveUserById(addedUser.id)
            val updatedUser : User = jsonToObject(updatedUserResponse.body.toString())
            TestCase.assertEquals(updatedName, updatedUser.name)
            TestCase.assertEquals(updatedEmail, updatedUser.email)

            deleteUser(addedUser.id)
        }

        @Test
        fun `updating a user when it doesn't exist, returns a 404 response`() {
            val updatedName = "Updated Name"
            val updatedEmail = "Updated Email"

            TestCase.assertEquals(404, updateUser(-1, updatedName, updatedEmail).status)
        }
    }

    @Nested
    inner class DeleteUsers {
        @Test
        fun `deleting a user when it doesn't exist, returns a 404 response`() {
            TestCase.assertEquals(404, deleteUser(-1).status)
        }

        @Test
        fun `deleting a user when it exists, returns a 204 response`() {

            val addResponse = addUser(validID, validName, validEmail)
            val addedUser: User = jsonToObject(addResponse.body.toString())
            TestCase.assertEquals(204, deleteUser(addedUser.id).status)
            TestCase.assertEquals(404, retrieveUserById(addedUser.id).status)
        }

    }
}