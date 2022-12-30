package ie.setu.controllers

import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonNodeToObject
import ie.setu.utils.jsonToObject
import junit.framework.TestCase
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


/**
 * This class is used to define the integrated activity API tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityAPITester : UserAPITester() {

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
    inner class CreateActivities {
        @Test
        fun `adding an activity for a existing user returning 201 response`() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            Assertions.assertEquals(201, response.status)
            deleteUser(newUser.id)
        }

        @Test
        fun `adding an activity for a non-existing user returning 404 response`() {
            val randInt = Int.MIN_VALUE
            TestCase.assertEquals(404, retrieveUserById(randInt).status)
            val response = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, randInt
            )
            TestCase.assertEquals(404, response.status)
        }
    }

    @Nested
    inner class ReadActivities {
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
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            addActivity(
                activities[1].id, activities[1].description,
                activities[1].duration, activities[1].calories, activities[1].started, newUser.id
            )
            addActivity(
                activities[2].id, activities[2].description,
                activities[2].duration, activities[2].calories, activities[2].started, newUser.id
            )
            val response = getActivitiesByUserID(newUser.id)
            TestCase.assertEquals(200, response.status)
            val returnActivities = jsonNodeToObject<Array<Activity>>(response)
            TestCase.assertEquals(3, returnActivities.size)
            TestCase.assertEquals(204, deleteActivityViaUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }
        @Test
        fun `get all activities for a user id when it doesnt exists and return 404 response`() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = getActivitiesByUserID(newUser.id)
            TestCase.assertEquals(404, response.status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `get all activities for a user id when id itself doesnt exists and return 404 response`() {
            val response = getActivitiesByUserID(Int.MIN_VALUE)
            TestCase.assertEquals(404, response.status)
        }

        @Test
        fun `get all activity by activity id when it doesnt exists and return 404 response`() {
            val response = getActivityByActivityID(Int.MIN_VALUE)
            TestCase.assertEquals(404, response.status)
        }


        @Test
        fun `get all activity by activity id when it exists and return 200 response`() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val addedActivity = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            TestCase.assertEquals(201, addedActivity.status)
            val activity = jsonNodeToObject<Activity>(addedActivity)
            val response = getActivityByActivityID(activity.id)
            TestCase.assertEquals(200, response.status)
            TestCase.assertEquals(204, deleteActivityViaActivityID(activity.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }
    }

    @Nested
    inner class UpdateActivities {
        @Test
        fun `updating an activity which doesnt exists, returns response 404`() {
            val randInt = Int.MIN_VALUE
            TestCase.assertEquals(404, retrieveUserById(randInt).status)
            TestCase.assertEquals(
                404,
                updateActivity(
                    randInt,
                    updatedDescription,
                    updatedDuration,
                    updatedCalories,
                    updatedStarted,
                    randInt
                ).status
            )
        }

        @Test
        fun `updating an activity when it exists, returns response 204`() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            TestCase.assertEquals(201, response.status)
            val addedActivity = jsonNodeToObject<Activity>(response)

            val updatedActivity = updateActivity(addedActivity.id, updatedDescription,
                updatedDuration, updatedCalories, updatedStarted, newUser.id)
            TestCase.assertEquals(204, updatedActivity.status)
            val fetchedUpdatedActivity = getActivityByActivityID(addedActivity.id)
            val activity = jsonNodeToObject<Activity>(fetchedUpdatedActivity)
            TestCase.assertEquals(updatedDescription, activity.description)
            TestCase.assertEquals(updatedDuration, activity.duration, 0.1)
            TestCase.assertEquals(updatedCalories, activity.calories)
            TestCase.assertEquals(updatedStarted, activity.started)
            TestCase.assertEquals(204, deleteActivityViaActivityID(addedActivity.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }
    }

    @Nested
    inner class DeleteActivities {
        @Test
        fun `deleting an activity by activity id when it doesn't exist, returns response 404`() {
            TestCase.assertEquals(404, deleteActivityViaActivityID(Int.MIN_VALUE).status)
        }

        @Test
        fun `deleting an activity by activity id when it exists, returns response 204 `() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            TestCase.assertEquals(201, response.status)
            val activity = jsonNodeToObject<Activity>(response)
            TestCase.assertEquals(204, deleteActivityViaActivityID(activity.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `deleting an activity by user id when it doesn't exist, returns response 404`() {
            TestCase.assertEquals(404, deleteActivityViaUserID(Int.MIN_VALUE).status)
        }
        @Test
        fun `deleting an activity by user id when it exists, returns response 204 `() {
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val activity1 = addActivity(
                activities[0].id, activities[0].description,
                activities[0].duration, activities[0].calories, activities[0].started, newUser.id
            )
            val activity2 = addActivity(
                activities[1].id, activities[1].description,
                activities[1].duration, activities[1].calories, activities[1].started, newUser.id
            )
            TestCase.assertEquals(201, activity1.status)
            TestCase.assertEquals(201, activity2.status)
            val addedActivity1 = jsonNodeToObject<Activity>(activity1)
            val addedActivity2 = jsonNodeToObject<Activity>(activity2)

            TestCase.assertEquals(204, deleteActivityViaActivityID(addedActivity1.id).status)
            TestCase.assertEquals(204, deleteActivityViaActivityID(addedActivity2.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }
    }
}

