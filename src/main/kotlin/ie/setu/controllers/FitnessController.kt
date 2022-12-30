package ie.setu.controllers

import ie.setu.domain.Fitness
import ie.setu.domain.repository.FitnessDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class is used to handle the requests for the fitness
 */
object FitnessController {
    private val userDao = UserDAO()
    private val fitnessDAO = FitnessDAO()


    @OpenApi(
        summary = "Fetches All Fitness details",
        operationId = "getAllFitnessDetails",
        tags = ["Fitness"],
        path = "/api/fitness",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Fitness>::class)])]
    )
    fun getAllFitnessDetails(ctx: Context) {
        val finesse = fitnessDAO.getAll()
        if (finesse.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(finesse)
    }

    @OpenApi(
        summary = "Fetches individuals fitness detail",
        operationId = "getFitnessByUserId",
        tags = ["Fitness"],
        path = "/api/{user-id}/fitness",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Fitness::class)])]
    )
    fun getFitnessByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val finesse = fitnessDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (finesse.isNotEmpty()) {
                ctx.json(finesse)
                ctx.status(200)
            }
            else{
                ctx.status(404)
            }
        }
        else{
            ctx.status(404)
        }
    }


    @OpenApi(
        summary = "Add Fitness activity",
        operationId = "addFitness",
        tags = ["Fitness"],
        path = "/api/fitness",
        method = HttpMethod.POST,
        responses = [OpenApiResponse("200", [OpenApiContent(Fitness::class)])]
    )
    fun addFitness(ctx: Context) {
        val fitObj : Fitness = jsonToObject(ctx.body())
        val userId = userDao.findById(fitObj.userId)
        if (userId != null) {
            fitnessDAO.save(fitObj)
            ctx.json(fitObj)
            ctx.status(201)
        }
        else{
            ctx.status(404)
        }
    }


    @OpenApi(
        summary = "Delete fitness details of User by User ID",
        operationId = "deleteAllFitByUserId",
        tags = ["Fitness"],
        path = "/api/{user-id}/fitness",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteAllFitByUserId(ctx: Context){
        if (fitnessDAO.deleteFitnessByUserId(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    @OpenApi(
        summary = "Delete fitness detail by ID",
        operationId = "deleteFitByID",
        tags = ["Fitness"],
        path = "/api/fitness/{fit-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("fit-id", Int::class, "Fitness ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteFitByID(ctx: Context){
        if (fitnessDAO.deleteFitnessByID(ctx.pathParam("fit-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Update fitness details by Id",
        operationId = "updateFitnessByID",
        tags = ["Fitness"],
        path = "/api/fitness/{fit-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("fit-id", Int::class, "Fitness ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun updateFitnessByID(ctx: Context){
        val fitObj : Fitness = jsonToObject(ctx.body())
        if (fitnessDAO.updateFitnessByID(ctx.pathParam("fit-id").toInt(), comingObj =fitObj) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    @OpenApi(
        summary = "Get individual fitness activity",
        operationId = "getFitByID",
        tags = ["Fitness"],
        path = "/api/fitness/{fit-id}",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Fitness::class)])]
    )
    fun getFitByID(ctx: Context) {
        val fitObj = fitnessDAO.findByFitnessId((ctx.pathParam("fit-id").toInt()))
        if (fitObj != null){
            ctx.json(fitObj)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

}