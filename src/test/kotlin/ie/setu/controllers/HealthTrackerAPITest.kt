package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.User
import junit.framework.TestCase.assertEquals
import kong.unirest.Unirest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ie.setu.utils.jsonToObject
import kotlin.test.DefaultAsserter.assertNotEquals
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.Activity
import ie.setu.helpers.*
import ie.setu.utils.jsonNodeToObject
import org.junit.jupiter.api.Assertions
import org.joda.time.DateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthTrackerControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    private fun addUser (id : Int, name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users")
            .body("{\"id\":\"$id\",\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }
    private fun deleteUser (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$id").asString()
    }

    private fun retrieveUserByEmail(email : String) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/email/${email}").asString()
    }

    //helper function to retrieve a test user from the database by id
    private fun retrieveUserById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${id}").asString()
    }

    private fun updateUser (id: Int, name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/$id")
            .body("{\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }

    private fun addActivity(actID : Int, desc: String, duration: Double, cal: Int, started: DateTime, userID: Int): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/activities")
            .body("""
                {
                  "id": $actID,
                   "description":"$desc",
                   "duration":$duration,
                   "calories":$cal,
                   "started":"$started",
                   "userId":$userID
                }
            """.trimIndent())
            .asJson()
    }


    private fun deleteActivityViaUserID(userID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$userID/activities").asString()
    }

    private fun deleteActivityViaActivityID(ActID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/activities/$ActID").asString()
    }

    private fun getAllActivities(): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/activities").asJson()
    }

    private fun getActivitiesByUserID(userID: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/users/${userID}/activities").asJson()
    }

    private fun getActivityByActivityID(ActID: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/activities/${ActID}").asJson()
    }

    private fun updateActivity(actID: Int, desc: String, duration: Double, cal: Int, started: DateTime, userID: Int): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/activities/$actID")
            .body("""
                {
                  "description":"$desc",
                  "duration":$duration,
                  "calories":$cal,
                  "started":"$started",
                  "userId":$userID
                }
            """.trimIndent()).asJson()
    }

    @Nested
    inner class ReadUsers {
        @Test
        fun `get all users from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/api/users/").asString()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<User> = jsonToObject(response.body.toString())
                assertEquals(0,retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `get user by id when user does not exist returns 404 response`() {

            //Arrange - test data for user id
            val id = Integer.MIN_VALUE

            // Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = Unirest.get(origin + "/api/users/${id}").asString()

            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `get user by email when user does not exist returns 404 response`() {
            // Arrange & Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = Unirest.get(origin + "/api/users/email/${nonExistingEmail}").asString()
            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }



        @Test
        fun `getting a user by id when id exists, returns a 200 response`() {

            //Arrange - add the user
            val addResponse = addUser(validID,validName, validEmail)
            val addedUser : User = jsonToObject(addResponse.body.toString())

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse = retrieveUserById(addedUser.id)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            deleteUser(addedUser.id)
        }

        @Test
        fun `getting a user by email when email exists, returns a 200 response`() {

            //Arrange - add the user
            addUser(validID,validName, validEmail)

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse = retrieveUserByEmail(validEmail)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            val retrievedUser : User = jsonToObject(retrieveResponse.body.toString())
            deleteUser(retrievedUser.id)
        }
    }




    @Nested
    inner class CreateUsers {
        @Test
        fun `add a user with correct details returns a 201 response`() {

            //Arrange & Act & Assert
            //    add the user and verify return code (using fixture data)
            val addResponse = addUser(validID,validName, validEmail)
            assertEquals(201, addResponse.status)

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse= retrieveUserByEmail(validEmail)
            assertEquals(200, retrieveResponse.status)

            //Assert - verify the contents of the retrieved user
            val retrievedUser : User = jsonToObject(addResponse.body.toString())
            assertEquals(validEmail, retrievedUser.email)
            assertEquals(validName, retrievedUser.name)

            //After - restore the db to previous state by deleting the added user
            val deleteResponse = deleteUser(retrievedUser.id)
            assertEquals(204, deleteResponse.status)
        }
    }

    @Nested
    inner class UpdateUsers {
        @Test
        fun `updating a user when it exists, returns a 204 response`() {

            //Arrange - add the user that we plan to do an update on

            val addResponse = addUser(validID,validName, validEmail)
            val addedUser : User = jsonToObject(addResponse.body.toString())

            //Act & Assert - update the email and name of the retrieved user and assert 204 is returned
            assertEquals(204, updateUser(addedUser.id, updatedName, updatedEmail).status)

            //Act & Assert - retrieve updated user and assert details are correct
            val updatedUserResponse = retrieveUserById(addedUser.id)
            val updatedUser : User = jsonToObject(updatedUserResponse.body.toString())
            assertEquals(updatedName, updatedUser.name)
            assertEquals(updatedEmail, updatedUser.email)

            //After - restore the db to previous state by deleting the added user
            deleteUser(addedUser.id)
        }

        @Test
        fun `updating a user when it doesn't exist, returns a 404 response`() {

            //Arrange - creating some text fixture data
            val updatedName = "Updated Name"
            val updatedEmail = "Updated Email"

            //Act & Assert - attempt to update the email and name of user that doesn't exist
            assertEquals(404, updateUser(-1, updatedName, updatedEmail).status)
        }
    }

    @Nested
    inner class DeleteUsers {
        @Test
        fun `deleting a user when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a user that doesn't exist
            assertEquals(404, deleteUser(-1).status)
        }

        @Test
        fun `deleting a user when it exists, returns a 204 response`() {

            //Arrange - add the user that we plan to do a delete on
            val addResponse = addUser(validID,validName, validEmail)
            val addedUser : User = jsonToObject(addResponse.body.toString())

            //Act & Assert - delete the added user and assert a 204 is returned
            assertEquals(204, deleteUser(addedUser.id).status)

            //Act & Assert - attempt to retrieve the deleted user --> 404 response
            assertEquals(404, retrieveUserById(addedUser.id).status)
        }

        @Nested
        inner class CreateActivities {
            //   post(  "/api/activities", HealthTrackerController::addActivity)

            @Test
            fun `adding an activity for a existing user returning 201 response`() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val response = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                Assertions.assertEquals(201, response.status)
                deleteUser(newUser.id)
            }

            @Test
            fun `adding an activity for a non-existing user returning 404 response`() {
                val randInt = Int.MIN_VALUE
                assertEquals(404, retrieveUserById(randInt).status)
                val response = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, randInt
                )
                assertEquals(404, response.status)
            }
        }

        @Nested
        inner class ReadActivities {
            //   get(   "/api/activities", HealthTrackerController::getAllActivities)
            //   get(   "/api/users/:user-id/activities", HealthTrackerController::getActivitiesByUserId)
            //   get(   "/api/activities/:activity-id", HealthTrackerController::getActivitiesByActivityId)

            @Test
            fun `get all activities with 200 or 404 response`() {
                val response = getAllActivities()

                if (response.status == 200){
                    val returnActivities = jsonNodeToObject<Array<Activity>>(response)
                    Assertions.assertNotEquals(0, returnActivities.size)
                }else{
                    Assertions.assertEquals(404, response.status)
                }
            }

            @Test
            fun `get all activities for a user id when it exists and return 200 response`() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                addActivity(activities[1].id,activities[1].description,
                    activities[1].duration, activities[1].calories, activities[1].started, newUser.id
                )
                addActivity(activities[2].id,activities[2].description,
                    activities[2].duration, activities[2].calories, activities[2].started, newUser.id
                )
                val response = getActivitiesByUserID(newUser.id)
                assertEquals(200, response.status)
                val returnActivities = jsonNodeToObject<Array<Activity>>(response)
                assertEquals(3, returnActivities.size)
                assertEquals(204, deleteActivityViaUserID(newUser.id).status)
                assertEquals(204, deleteUser(newUser.id).status)
            }
            @Test
            fun `get all activities for a user id when it doesnt exists and return 404 response`() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val response = getActivitiesByUserID(newUser.id)
                assertEquals(404, response.status)
                assertEquals(204, deleteUser(newUser.id).status)
            }

            @Test
            fun `get all activities for a user id when id itself doesnt exists and return 404 response`() {
                val response = getActivitiesByUserID(Int.MIN_VALUE)
                assertEquals(404, response.status)
            }

            @Test
            fun `get all activity by activity id when it doesnt exists and return 404 response`() {
                val response = getActivityByActivityID(Int.MIN_VALUE)
                assertEquals(404, response.status)
            }


            @Test
            fun `get all activity by activity id when it exists and return 200 response`() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val addedActivity = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                assertEquals(201, addedActivity.status)
                val activity = jsonNodeToObject<Activity>(addedActivity)
                val response = getActivityByActivityID(activity.id)
                assertEquals(200, response.status)
                assertEquals(204, deleteActivityViaActivityID(activity.id).status)
                assertEquals(204, deleteUser(newUser.id).status)
            }
        }

        @Nested
        inner class UpdateActivities {
            //  patch( "/api/activities/:activity-id", HealthTrackerController::updateActivity)

            @Test
            fun `updating an activity which doesnt exists, returns response 404`() {
                val randInt = Int.MIN_VALUE
                assertEquals(404, retrieveUserById(randInt).status)
                assertEquals(404, updateActivity(randInt,updatedDescription, updatedDuration,updatedCalories, updatedStarted, randInt).status
                )
            }

            @Test
            fun `updating an activity when it exists, returns response 204`() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val response = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                assertEquals(201, response.status)
                val addedActivity = jsonNodeToObject<Activity>(response)

                val updatedActivity = updateActivity(addedActivity.id, updatedDescription,
                    updatedDuration, updatedCalories, updatedStarted, newUser.id)
                assertEquals(204, updatedActivity.status)
                val fetchedUpdatedActivity = getActivityByActivityID(addedActivity.id)
                val activity = jsonNodeToObject<Activity>(fetchedUpdatedActivity)
                assertEquals(updatedDescription, activity.description)
                assertEquals(updatedDuration, activity.duration, 0.1)
                assertEquals(updatedCalories, activity.calories)
                assertEquals(updatedStarted, activity.started)
                assertEquals(204,deleteActivityViaActivityID(addedActivity.id).status)
                assertEquals(204,deleteUser(newUser.id).status)
            }
        }

        @Nested
        inner class DeleteActivities {
            //   delete("/api/activities/:activity-id", HealthTrackerController::deleteActivityByActivityId)
            //   delete("/api/users/:user-id/activities", HealthTrackerController::deleteActivityByUserId)

            @Test
            fun `deleting an activity by activity id when it doesn't exist, returns response 404`() {
                assertEquals(404, deleteActivityViaActivityID(Int.MIN_VALUE).status)
            }

            @Test
            fun `deleting an activity by activity id when it exists, returns response 204 `() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val response = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                assertEquals(201, response.status)
                val activity = jsonNodeToObject<Activity>(response)
                assertEquals(204, deleteActivityViaActivityID(activity.id).status)
                assertEquals(204,deleteUser(newUser.id).status)
            }

            @Test
            fun `deleting an activity by user id when it doesn't exist, returns response 404`() {
                assertEquals(404, deleteActivityViaUserID(Int.MIN_VALUE).status)
            }
            @Test
            fun `deleting an activity by user id when it exists, returns response 204 `() {
                val newUser : User = jsonToObject(addUser(validID,validName, validEmail).body.toString())
                val activity1 = addActivity(activities[0].id,activities[0].description,
                    activities[0].duration, activities[0].calories, activities[0].started, newUser.id
                )
                val activity2 = addActivity(activities[1].id,activities[1].description,
                    activities[1].duration, activities[1].calories, activities[1].started, newUser.id
                )
                assertEquals(201, activity1.status)
                assertEquals(201, activity2.status)
                val addedActivity1 = jsonNodeToObject<Activity>(activity1)
                val addedActivity2 = jsonNodeToObject<Activity>(activity2)

                assertEquals(204, deleteActivityViaActivityID(addedActivity1.id).status)
                assertEquals(204, deleteActivityViaActivityID(addedActivity2.id).status)
                assertEquals(204, deleteUser(newUser.id).status)
            }
        }
    }
}
