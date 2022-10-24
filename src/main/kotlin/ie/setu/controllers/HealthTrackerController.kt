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

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()

    @OpenApi(
        summary = "Fetches All Users",
        operationId = "getAllUsers",
        tags = ["User"],
        path = "/api/users",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<User>::class)])]
    )
    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
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
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
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
        userDao.delete(ctx.pathParam("user-id").toInt())
        ctx.json(userDao.getAll())
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
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.update(ctx.pathParam("user-id").toInt(), user = user)
        ctx.json(user)
    }


    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( activityDAO.getAll() ))
    }

    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }

    fun addActivity(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }
}