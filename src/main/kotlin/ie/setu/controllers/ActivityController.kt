package ie.setu.controllers

import ie.setu.domain.Activity
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*


/**
 * This class is used to handle the requests for the activities
 */
object ActivityController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()

    @OpenApi(
        summary = "Fetches All activities",
        operationId = "getAllActivities",
        tags = ["Activity"],
        path = "/api/activities",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Activity>::class)])]
    )
    fun getAllActivities(ctx: Context) {
        val activities = activityDAO.getAll()
        if (activities.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(activities)
    }

    @OpenApi(
        summary = "Fetches individual activity",
        operationId = "getActivitiesByUserId",
        tags = ["Activity"],
        path = "/api/{user-id}/activities",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Activity::class)])]
    )
    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                ctx.json(activities)
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
        summary = "Add Activity",
        operationId = "addActivity",
        tags = ["Activity"],
        path = "/api/activities",
        method = HttpMethod.POST,
        responses = [OpenApiResponse("200", [OpenApiContent(Activity::class)])]
    )
    fun addActivity(ctx: Context) {
        val activity : Activity = jsonToObject(ctx.body())
        val userId = userDao.findById(activity.userId)
        if (userId != null) {
            activityDAO.save(activity)
            ctx.json(activity)
            ctx.status(201)
        }
        else{
            ctx.status(404)
        }
    }


    @OpenApi(
        summary = "Delete Activities of User by User ID",
        operationId = "deleteAllActByUserId",
        tags = ["Activity"],
        path = "/api/{user-id}/activities",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteAllActByUserId(ctx: Context){
        if (activityDAO.deleteActivitiesOfUserID(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    @OpenApi(
        summary = "Delete Activities of User by Activity ID",
        operationId = "deleteActByID",
        tags = ["Activity"],
        path = "/api/activities/{act-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("act-id", Int::class, "Activity ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun deleteActByID(ctx: Context){
        if (activityDAO.deleteActivityByID(ctx.pathParam("act-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Update Activity by its ID",
        operationId = "updateActivityByActID",
        tags = ["Activity"],
        path = "/api/activities/{act-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("act-id", Int::class, "Activity ID")],
        responses  = [OpenApiResponse("204")]
    )
    fun updateActivityByActID(ctx: Context){
        val activity : Activity = jsonToObject(ctx.body())
        if (activityDAO.updateActivityByID(ctx.pathParam("act-id").toInt(), comingAct =activity) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Get individual activity",
        operationId = "getActByActID",
        tags = ["Activity"],
        path = "/api/activities/{act-id}",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Activity::class)])]
    )
    fun getActByActID(ctx: Context) {
        val activity = activityDAO.findByActivityId((ctx.pathParam("act-id").toInt()))
        if (activity != null){
            ctx.json(activity)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }
}