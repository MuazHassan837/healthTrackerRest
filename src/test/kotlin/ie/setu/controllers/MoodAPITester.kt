package ie.setu.controllers

import ie.setu.domain.Mood
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonNodeToObject
import ie.setu.utils.jsonToObject
import junit.framework.TestCase
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

private val moodObj1 = moodObjs.get(0)

/**
 * This class is used to define the mood API tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MoodAPITester : UserAPITester() {


    private fun addMood(ID : Int, newMood: String, userId: Int, started: DateTime): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/moods").body("""
                {
                  "id": $ID,
                   "mood":"$newMood",
                   "userId":$userId,
                   "started":"$started"
                }
            """.trimIndent())
            .asJson()
    }

    private fun delMoodByUserID(userID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$userID/moods").asString()
    }
    private fun delMoodByID(comingID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/moods/$comingID").asString()
    }
    private fun getMoodByID(comingId: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/moods/${comingId}").asJson()
    }
    private fun getAllMoods(): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/moods").asJson()
    }
    private fun getMoodForUserID(userID: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/users/${userID}/moods").asJson()
    }

    private fun updateMood(ID : Int, newMood: String, userId: Int, started: DateTime): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/moods/$ID")
            .body("""
                {
                   "mood":"$newMood",
                   "userId":$userId,
                   "started":"$started"
                }
            """.trimIndent()).asJson()
    }

    private fun addOneMoodAndUser(){
        val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
        val response = addMood(ID = moodObj1.id, moodObj1.mood,newUser.id, moodObj1.started)
        TestCase.assertEquals(201, response.status)
    }
    private fun destructorToMoodAndOneUser(){
        TestCase.assertEquals(204, delMoodByUserID(validID).status)
        TestCase.assertEquals(204, deleteUser(validID).status)
    }


    @Nested
    inner class CreateMoodObj {
        @Test
        fun `add an mood for existing user returns 201 response`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addMood(ID = moodObj1.id, moodObj1.mood,newUser.id, moodObj1.started)
            TestCase.assertEquals(201, response.status)

            TestCase.assertEquals(204, delMoodByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }


        @Test
        fun `add an mood update for non existing user returns 404 response`(){
            val response = addMood(ID = moodObj1.id, moodObj1.mood, Int.MIN_VALUE, moodObj1.started)
            TestCase.assertEquals(404, response.status)
        }


    }


    @Nested
    inner class ReadMoodObj {

        @Test
        fun `read existing mood list returns 200`(){
            addOneMoodAndUser()
            TestCase.assertEquals(200, getAllMoods().status)
            destructorToMoodAndOneUser()
        }

        @Test
        fun `read non-existing mood list return 404`(){
            TestCase.assertEquals(getAllMoods().status, 404)
        }

        @Test
        fun `read mood for user id when it doesnt exists return 404`(){
            TestCase.assertEquals(404, getMoodForUserID(Int.MIN_VALUE).status)
        }


        @Test
        fun `read mood for user id when it exists returns 200`(){
            addOneMoodAndUser()
            TestCase.assertEquals(200, getMoodForUserID(validID).status)
            destructorToMoodAndOneUser()
        }


        @Test
        fun `read mood for user id when it doesnt exists returns 404`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())

            TestCase.assertEquals(404, getMoodForUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `read mood with id when it exists returns 200`(){
            addOneMoodAndUser()
            TestCase.assertEquals(200, getMoodByID(moodObj1.id).status)
            destructorToMoodAndOneUser()
        }

        @Test
        fun `read invalid mood with id return 404`(){
            TestCase.assertEquals(404, getMoodByID(Int.MIN_VALUE).status)
        }


        @Nested
        inner class DelMoodObj {
            @Test
            fun `delete mood of valid user id returns 204`(){
                val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
                val response = addMood(ID = moodObj1.id, moodObj1.mood,newUser.id, moodObj1.started)
                TestCase.assertEquals(201, response.status)

                TestCase.assertEquals(201, response.status)

                TestCase.assertEquals(204, delMoodByUserID(newUser.id).status)
                TestCase.assertEquals(204, deleteUser(newUser.id).status)
            }


            @Test
            fun `delete mood of invalid user returns 404`(){
                TestCase.assertEquals(404, delMoodByUserID(Int.MIN_VALUE).status)
            }

            @Test
            fun` delete mood by id for valid mood id returns 204`(){
                val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
                val response = addMood(ID = moodObj1.id, moodObj1.mood,newUser.id, moodObj1.started)
                TestCase.assertEquals(201, response.status)

                val returnResponse = jsonNodeToObject<Mood>(response)

                TestCase.assertEquals(204, delMoodByID(returnResponse.id).status)
                TestCase.assertEquals(204, deleteUser(newUser.id).status)
            }

            @Test
            fun `delete mood by id for invalid mood id return 404`(){
                TestCase.assertEquals(404, delMoodByID(Int.MIN_VALUE).status)
            }
        }

        @Nested
        inner class UpdateMoodObj {
            @Test
            fun `update valid mood by id return 204`(){
                val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
                val response = addMood(ID = moodObj1.id, moodObj1.mood,newUser.id, moodObj1.started)
                TestCase.assertEquals(201, response.status)

                TestCase.assertEquals(
                    204,
                    updateMood(ID = moodObj1.id, newMood = updateMood, userId = newUser.id,updatedStarted).status
                )

                TestCase.assertEquals(204, delMoodByUserID(newUser.id).status)
                TestCase.assertEquals(204, deleteUser(newUser.id).status)
            }

            @Test
            fun `update mood by valid id returns 404`(){
                TestCase.assertEquals(404, updateMood(ID = InValidNo, newMood = "Much Wow", userId = InValidNo,updatedStarted).status)
            }
        }
    }
}