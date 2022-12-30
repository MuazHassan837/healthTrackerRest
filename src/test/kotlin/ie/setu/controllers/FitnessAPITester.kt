package ie.setu.controllers

import ie.setu.domain.Fitness
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
 * This class is used to define the fitness API tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitnessAPITester :UserAPITester() {


    private fun addFitness(id : Int, FitnessType: String, started: DateTime, userId: Int): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/fitness").body("""
                {
                  "id": $id,
                   "dayType":"$FitnessType",
                   "started":"$started",
                   "userId":$userId
                }
            """.trimIndent())
            .asJson()
    }
    private fun delFitnessByUserID(userID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$userID/fitness").asString()
    }
    private fun delFitnessByID(comingID: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/fitness/$comingID").asString()
    }
    private fun getFitnessByID(comingId: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/fitness/${comingId}").asJson()
    }
    private fun getAllFitnessInfo(): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/fitness").asJson()
    }

    private fun getFitnessForUserID(userID: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/users/${userID}/fitness").asJson()
    }
    private fun updateFitness(id : Int, FitnessType: String, started: DateTime, userId: Int): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/fitness/$id")
            .body("""
                {
                   "dayType":"$FitnessType",
                   "started":"$started",
                   "userId":$userId
                }
            """.trimIndent()).asJson()
    }

    private fun addUserAndFitness(){
        val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
        val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
            started = fitnessObjs[0].started, userId = validID)
        TestCase.assertEquals(201, response.status)
    }
    private fun destructor(){
        TestCase.assertEquals(204, delFitnessByUserID(validID).status)
        TestCase.assertEquals(204, deleteUser(validID).status)
    }

    @Nested
    inner class CreateFitness {
        @Test
        fun `add an fitness update for existing user returns 201 response`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = validID)
            TestCase.assertEquals(201, response.status)

            TestCase.assertEquals(204, delFitnessByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `add an fitness update for non existing user returns 404 response`(){
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = Int.MIN_VALUE)
            TestCase.assertEquals(404, response.status)
        }
    }


    @Nested
    inner class ReadFitness {
        @Test
        fun `read existing fitness list returns 200`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = validID)
            TestCase.assertEquals(201, response.status)

            val response1 = addFitness(id = fitnessObjs[1].id, fitnessObjs[1].dayType,
                started = fitnessObjs[1].started, userId = newUser.id)
            TestCase.assertEquals(201, response1.status)

            TestCase.assertEquals(200, getAllFitnessInfo().status)

            TestCase.assertEquals(204, delFitnessByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `read non-existing fitness list return 404`(){
            TestCase.assertEquals(404,getAllFitnessInfo().status)
        }

        @Test
        fun `read fitness for user id when it doesnt exists return 404`(){
            TestCase.assertEquals(404, getFitnessForUserID(Int.MIN_VALUE).status)
        }


        @Test
        fun `read fitness for user id when it exists returns 200`(){
            addUserAndFitness()
            TestCase.assertEquals(200, getFitnessForUserID(validID).status)
            destructor()
        }

        @Test
        fun `read fitness for user id when it exists but fitness list doesnt returns 404`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            TestCase.assertEquals(404, getFitnessForUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `read fitness with id when it exists returns 200`(){
            addUserAndFitness()
            TestCase.assertEquals(200, getFitnessByID(fitnessObjs[0].id).status)
            destructor()
        }

        @Test
        fun `read invalid fitness object with id return 404`(){
            TestCase.assertEquals(404, getFitnessByID(Int.MIN_VALUE).status)
        }
    }


    @Nested
    inner class DeleteFitness {
        @Test
        fun `delete fitness of valid user id returns 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = newUser.id)
            TestCase.assertEquals(201, response.status)

            TestCase.assertEquals(204, delFitnessByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `delete fitness of invalid user returns 404`(){
            TestCase.assertEquals(404, delFitnessByUserID(Int.MIN_VALUE).status)
        }

        @Test
        fun` delete fitness by id for valid fitness id returns 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = newUser.id)
            TestCase.assertEquals(201, response.status)

            val returnResponse = jsonNodeToObject<Fitness>(response)

            TestCase.assertEquals(204, delFitnessByID(returnResponse.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `delete fitness by id for invalid id return 404`(){
            TestCase.assertEquals(404, delFitnessByID(Int.MIN_VALUE).status)
        }
    }

    @Nested
    inner class UpdateFitness {
        @Test
        fun `update valid fitness by id return 204`(){
            val newUser : User = jsonToObject(addUser(validID, validName, validEmail).body.toString())
            val response = addFitness(id = fitnessObjs[0].id, fitnessObjs[0].dayType,
                started = fitnessObjs[0].started, userId = newUser.id)
            TestCase.assertEquals(201, response.status)
            TestCase.assertEquals(
                204,
                updateFitness(fitnessObjs[0].id, updatedFitType, fitnessObjs[0].started, newUser.id).status
            )
            TestCase.assertEquals(204, delFitnessByUserID(newUser.id).status)
            TestCase.assertEquals(204, deleteUser(newUser.id).status)
        }

        @Test
        fun `update fitness by valid id returns 404`(){
            val InValidNo = Int.MIN_VALUE
            TestCase.assertEquals(404, updateFitness(InValidNo, updatedFitType, DateTime.now(), InValidNo).status)
        }
    }
}