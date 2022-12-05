package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context
import ie.setu.domain.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Activity
import ie.setu.domain.repository.ActivityDAO
import io.javalin.plugin.openapi.annotations.*
import com.fasterxml.jackson.datatype.joda.JodaModule
import ie.setu.domain.InTake
import ie.setu.domain.repository.InTakeDAO
import ie.setu.utils.jsonToObject

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()
    private val hydrationDAO = InTakeDAO()

    @OpenApi(
        summary = "Fetches All Users",
        operationId = "getAllUsers",
        tags = ["User"],
        path = "/api/users",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<User>::class)])]
    )
    fun getAllUsers(ctx: Context) {
        val returnedUser =  userDao.getAll()

        if (returnedUser.size != 0) {
            ctx.status(200)
        }else {
            ctx.status(404)
        }
        ctx.json(returnedUser)
    }

    @OpenApi(
        summary = "Fetch User by ID",
        operationId = "getUserById",
        tags = ["User"],
        path = "/api/users/{user-id}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }else {
            ctx.status(404)
        }
    }

    @OpenApi(
        summary = "Add New User",
        operationId = "addUser",
        tags = ["User"],
        path = "/api/users",
        method = HttpMethod.POST,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("200")]
    )

    fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        }
    }

    @OpenApi(
        summary = "Fetch User by Email",
        operationId = "getUserByEmail",
        tags = ["User"],
        path = "/api/users/email/{email}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("email", String::class, "User Email")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    fun getUserByEmail(ctx: Context){
        val userByMail = userDao.findByEmail(ctx.pathParam("email").toString())
        if (userByMail != null){
            ctx.json(userByMail)
            ctx.status(200)
        }else {
            ctx.status(404)
        }
    }

    @OpenApi(
        summary = "Delete User by ID",
        operationId = "deleteUser",
        tags = ["User"],
        path = "/api/users/{user-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )

    fun deleteUser(ctx: Context){
        if (userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    @OpenApi(
        summary = "Update User by ID",
        operationId = "updateUser",
        tags = ["User"],
        path = "/api/users/{user-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("user-id", Int::class, "User ID")],
        responses  = [OpenApiResponse("204")]
    )

    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.update(id = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)

        ctx.json(userDao.getAll())
    }



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


    // InTake (Seperate File to be made)

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