package ie.setu.controllers

import ie.setu.domain.InTake
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

/**
 * This class is used to define the hydration API tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HydrationAPITester :UserAPITester() {

    private fun addInTake(ID : Int, amount: Double, content: String, userId: Int, started: DateTime): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/intakes").body("""
                {
                  "id": $ID,
                   "amountltr":$amount,
                   "substance":"$content",
                   "userId":$userId,
                   "started":"$started"
                }
            """.trimIndent())
            .asJson()
    }

    private fun delIntakeByUserID(userID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$userID/intakes").asString()
    }

    private fun delIntakeByID(comingID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/intakes/$comingID").asString()
    }

    private fun getInTakeByID(comingId: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/intakes/${comingId}").asJson()
    }
    private fun getAllInTakes(): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/intakes").asJson()
    }

    private fun getInTakeForUserID(userID: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/users/${userID}/intakes").asJson()
    }

    private fun updateIntake(ID : Int, amount: Double, content: String, userId: Int, started: DateTime): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/intakes/$ID")
            .body("""
                {
                   "amountltr":$amount,
                   "substance":"$content",
                   "userId":$userId,
                   "started":"$started"
                }
            """.trimIndent()).asJson()
    }

    private fun addOneUserAndIntake(){
        val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
        val response = addInTake(
            intakes[0].id, intakes[0].amountltr,
            intakes[0].substance, newUser.id, intakes[0].started)
        TestCase.assertEquals(201, response.status)
    }
    private fun destructor(){
        TestCase.assertEquals(204, delIntakeByUserID(validID).status)
        TestCase.assertEquals(204, deleteUser(validID).status)
    }


    @Nested
    inner class CreateHydrationObj {

        @Test
        fun `add an hydration update for existing user returns 201 response`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, newUser.id, intakes[0].started)
            TestCase.assertEquals(201, response.status)

            TestCase.assertEquals(204, delIntakeByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }


        @Test
        fun `add an hydration update for non existing user returns 404 response`(){
            val response = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, Int.MIN_VALUE, intakes[0].started)
            TestCase.assertEquals(404, response.status)
        }

    }

    @Nested
    inner class ReadHydrationObj {

        @Test
        fun `read existing hydration list returns 200`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response1 = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, newUser.id, intakes[0].started)
            TestCase.assertEquals(201, response1.status)
            val response2 = addInTake(
                intakes[1].id, intakes[1].amountltr,
                intakes[1].substance, newUser.id, intakes[1].started)
            TestCase.assertEquals(201, response2.status)
            TestCase.assertEquals(200, getAllInTakes().status)

            TestCase.assertEquals(204, delIntakeByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `read non-existing hydration list return 404`(){
            TestCase.assertEquals(getAllInTakes().status, 404)
        }


        @Test
        fun `read Intake for user id when it doesnt exists return 404`(){
            TestCase.assertEquals(404, getInTakeForUserID(Int.MIN_VALUE).status)
        }

        @Test
        fun `read intake for user id when it and intake exists returns 200`(){
            addOneUserAndIntake()
            TestCase.assertEquals(200, getInTakeForUserID(validID).status)
            destructor()
        }

        @Test
        fun `read intake for user id when it exists but intake list doesnt returns 404`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            TestCase.assertEquals(404, getInTakeForUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `read intake with id when it exists returns 200`(){
            addOneUserAndIntake()
            TestCase.assertEquals(200, getInTakeByID(intakes[0].id).status)
            destructor()
        }

        @Test
        fun `read invalid intake with id return 404`(){
            TestCase.assertEquals(404, getInTakeByID(Int.MIN_VALUE).status)
        }
    }


    @Nested
    inner class DelHydrationObj {

        @Test
        fun `delete intakes of valid user id returns 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response1 = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, newUser.id, intakes[0].started)
            TestCase.assertEquals(201, response1.status)
            TestCase.assertEquals(204, delIntakeByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `delete intakes of invalid user returns 404`(){
            TestCase.assertEquals(404, delIntakeByUserID(Int.MIN_VALUE).status)
        }


        @Test
        fun` delete intake by id for valid intake id returns 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response1 = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, newUser.id, intakes[0].started)
            TestCase.assertEquals(201, response1.status)
            val returnResponse = jsonNodeToObject<InTake>(response1)
            TestCase.assertEquals(204, delIntakeByID(returnResponse.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `delete intake by id for invalid intake id return 404`(){
            TestCase.assertEquals(404, delIntakeByID(Int.MIN_VALUE).status)
        }




    }

    @Nested
    inner class UpdateHydrationObj{

        @Test
        fun `update valid Intake by id return 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response1 = addInTake(
                intakes[0].id, intakes[0].amountltr,
                intakes[0].substance, newUser.id, intakes[0].started)
            TestCase.assertEquals(201, response1.status)

            val returnResponse = jsonNodeToObject<InTake>(response1)
            TestCase.assertEquals(
                204,
                updateIntake(returnResponse.id, updatedAmount, updatedSubstance, returnResponse.userId,updatedStarted).status
            )

            val fetchedIntake =  getInTakeByID(returnResponse.id)
            val convertedFetchedIntake = jsonNodeToObject<InTake>(fetchedIntake)
            TestCase.assertEquals(updatedAmount, convertedFetchedIntake.amountltr)
            TestCase.assertEquals(updatedSubstance, convertedFetchedIntake.substance)
            TestCase.assertEquals(204, delIntakeByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `update intake by invalid id returns 404`(){
            TestCase.assertEquals(404, updateIntake(InValidNo, updatedAmount, updatedSubstance, InValidNo,updatedStarted).status)
        }

    }
}

