package ie.setu.controllers

import ie.setu.domain.InTake
import ie.setu.domain.repository.InTakeDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class is used to handle the requests for the hydration
 */
object HydrationController {
    private val userDao = UserDAO()
    private val hydrationDAO = InTakeDAO()

    @OpenApi(
        summary = "Fetches All Intakes",
        operationId = "getAllInTakes",
        tags = ["InTake"],
        path = "/api/intakes",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<InTake>::class)])]
    )

    fun getAllInTakes(ctx: Context) {
        val intakes = hydrationDAO.getAll()
        if (intakes.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(intakes)
    }

    @OpenApi(
        summary = "Fetches individual users intake",
        operationId = "getInTakeByUserId",
        tags = ["InTake"],
        path = "/api/{user-id}/intakes",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(InTake::class)])]
    )
    fun getInTakeByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val intakes = hydrationDAO.findByUserID(ctx.pathParam("user-id").toInt())
            if (intakes.isNotEmpty()) {
                ctx.json(intakes)
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
        summary = "Add Hydration InTake",
        operationId = "addInTake",
        tags = ["InTake"],
        path = "/api/intakes",
        method = HttpMethod.POST,
        responses = [OpenApiResponse("200", [OpenApiContent(InTake::class)])]
    )
    fun addInTake(ctx: Context) {
        val intake : InTake = jsonToObject(ctx.body())
        val userId = userDao.findById(intake.userId)
        if (userId != null) {
            hydrationDAO.save(intake)
            ctx.json(intake)
            ctx.status(201)
        }
        else{
            ctx.status(404)
        }
    }

    @OpenApi(
        summary = "Delete InTake by UserID",
        operationId = "delAllInTakeByUserId",
        tags = ["InTake"],
        path = "/api/{user-id}/intakes",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun delAllInTakeByUserId(ctx: Context){
        if (hydrationDAO.deleteIntakesOfUserID(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Delete intakes of User by intake ID",
        operationId = "delIntakeByID",
        tags = ["InTake"],
        path = "/api/intakes/{hydra-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("hydra-id", Int::class, "Hydration ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun delIntakeByID(ctx: Context){
        if (hydrationDAO.deleteInTakeByID(ctx.pathParam("hydra-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Update InTake by its ID",
        operationId = "updateInTakeBytID",
        tags = ["InTake"],
        path = "/api/intakes/{hydra-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("hydra-id", Int::class, "Hydration ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun updateInTakeByID(ctx: Context){
        val intake : InTake = jsonToObject(ctx.body())
        if (hydrationDAO.updateInTakeById(ctx.pathParam("hydra-id").toInt(), newObj =intake) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Get individual InTake",
        operationId = "getIntakeByID",
        tags = ["InTake"],
        path = "/api/intakes/{hydra-id}",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(InTake::class)])]
    )
    fun getIntakeByID(ctx: Context) {
        val intake = hydrationDAO.findByInTakeId((ctx.pathParam("hydra-id").toInt()))
        if (intake != null){
            ctx.json(intake)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }
}