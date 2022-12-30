package ie.setu.controllers

import ie.setu.domain.Mood
import ie.setu.domain.repository.MoodDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class is used to handle the requests for the mood
 */
object MoodController {

    private val userDao = UserDAO()
    private val moodDAO = MoodDAO()

    @OpenApi(
        summary = "Fetches All mood",
        operationId = "getAllMoods",
        tags = ["Mood"],
        path = "/api/moods",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Mood>::class)])]
    )
    fun getAllMoods(ctx: Context) {
        val moodsObj = moodDAO.getAll()
        if (moodsObj.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(moodsObj)
    }

    @OpenApi(
        summary = "Fetches individual user's moods",
        operationId = "getMoodByUserId",
        tags = ["Mood"],
        path = "/api/{user-id}/moods",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Mood::class)])]
    )
    fun getMoodByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val moods = moodDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (moods.isNotEmpty()) {
                ctx.json(moods)
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
        summary = "Add mood",
        operationId = "addMood",
        tags = ["Mood"],
        path = "/api/moods",
        method = HttpMethod.POST,
        responses = [OpenApiResponse("200", [OpenApiContent(Mood::class)])]
    )
    fun addMood(ctx: Context) {
        val mood : Mood = jsonToObject(ctx.body())
        val userId = userDao.findById(mood.userId)
        if (userId != null) {
            moodDAO.save(mood)
            ctx.json(mood)
            ctx.status(201)
        }
        else{
            ctx.status(404)
        }
    }

    @OpenApi(
        summary = "Delete mood by User ID",
        operationId = "deleteAllMoodByUserId",
        tags = ["Mood"],
        path = "/api/{user-id}/moods",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteAllMoodByUserId(ctx: Context){
        if (moodDAO.delMoodByUserId(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Delete moods of User by ID",
        operationId = "deleteMoodByID",
        tags = ["Mood"],
        path = "/api/moods/{mood-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("mood-id", Int::class, "Mood ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteMoodByID(ctx: Context){
        if (moodDAO.delMoodById(ctx.pathParam("mood-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Update mood by its ID",
        operationId = "updateMoodByID",
        tags = ["Mood"],
        path = "/api/moods/{mood-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("mood-id", Int::class, "Mood ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun updateMoodByID(ctx: Context){
        val mood : Mood = jsonToObject(ctx.body())
        if (moodDAO.updateMoodById(ctx.pathParam("mood-id").toInt(), comingObj = mood) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Get individual mood",
        operationId = "getMoodByID",
        tags = ["Mood"],
        path = "/api/moods/{mood-id}",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Mood::class)])]
    )
    fun getMoodByID(ctx: Context) {
        val moodObj = moodDAO.findById((ctx.pathParam("mood-id").toInt()))
        if (moodObj != null){
            ctx.json(moodObj)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }
}