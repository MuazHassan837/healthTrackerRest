package ie.setu.config
import ie.setu.domain.ErrorResponse

import cc.vileda.openapi.dsl.info
import cc.vileda.openapi.dsl.openapiDsl
import ie.setu.controllers.*
import ie.setu.utils.jsonObjectMapper

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.javalin.plugin.rendering.vue.VueComponent


/**
 * This class is used to configure the Javalin service
 */
class JavalinConfig {

    /**
     * This method is use to start the javalin service
     * @return Javalin provides a fluent API Config for configuring the server
     */
    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            it.registerPlugin(getConfiguredOpenApiPlugin())
            it.defaultContentType = "application/json"
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.enableWebjars()
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(getRemoteAssignedPort())
        registerRoutes(app)
        return app
    }

    /**
     * This method is used to assign the port
     */

    private fun getRemoteAssignedPort(): Int {
        val herokuPort = System.getenv("PORT")
        return if (herokuPort != null) {
            Integer.parseInt(herokuPort)
        } else 7000
    }

    /**
     * This method is used to register the routes
     */
    private fun registerRoutes(app: Javalin) {
        app.routes {
            // add open api documentation for "/" endpoint
//
            get("/", VueComponent("<home-page></home-page>"))
            get("/users", VueComponent("<user-overview></user-overview>"))
            get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
            get("/users/{user-id}/fitness", VueComponent("<jet-fit-page></jet-fit-page>"))
            get("/jetfit", VueComponent("<user-fitness-page></user-fitness-page>"))



            path("/api/users") {
                get(UserController::getAllUsers)
                post(UserController::addUser)
                path("{user-id}"){
                    get(UserController::getUserByUserId)
                    delete(UserController::deleteUser)
                    patch(UserController::updateUser)

                    path("activities"){
                        get(ActivityController::getActivitiesByUserId)
                        delete(ActivityController::deleteAllActByUserId)
                    }
                    path("intakes"){
                        get(HydrationController::getInTakeByUserId)
                        delete(HydrationController::delAllInTakeByUserId)
                    }
                    path("fitness"){
                        get(FitnessController::getFitnessByUserId)
                        delete(FitnessController::deleteAllFitByUserId)
                    }
                    path("moods"){
                        get(MoodController::getMoodByUserId)
                        delete(MoodController::deleteAllMoodByUserId)
                    }
                }
                path("/email/{email}"){
                    get(UserController::getUserByEmail)
                }
            }

            path("/api/activities") {
                path("/{act-id}"){
                    delete(ActivityController::deleteActByID)
                    patch(ActivityController::updateActivityByActID)
                    get(ActivityController::getActByActID)
                }
                get(ActivityController::getAllActivities)
                post(ActivityController::addActivity)
            }

            path("/api/intakes") {
                path("/{hydra-id}"){
                    delete(HydrationController::delIntakeByID)
                    patch(HydrationController::updateInTakeByID)
                    get(HydrationController::getIntakeByID)
                }
                get(HydrationController::getAllInTakes)
                post(HydrationController::addInTake)
            }

            path("/api/fitness") {
                path("/{fit-id}"){
                    delete(FitnessController::deleteFitByID)
                    patch(FitnessController::updateFitnessByID)
                    get(FitnessController::getFitByID)
                }
                get(FitnessController::getAllFitnessDetails)
                post(FitnessController::addFitness)
            }

            path("/api/moods") {
                path("/{mood-id}"){
                    delete(MoodController::deleteMoodByID)
                    patch(MoodController::updateMoodByID)
                    get(MoodController::getMoodByID)
                }
                get(MoodController::getAllMoods)
                post(MoodController::addMood)
            }

        }
    }
}

/**
 * This method is used to configure the OpenApiPlugin
 */
fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
    OpenApiOptions {
        openapiDsl {
            info {
                title = "Health Tracker App"
                description = "Health Tracker API"
                version = "2.0.0"
            }
        }
    }.apply {
        path("/swagger-docs")
        swagger(SwaggerOptions("/swagger-ui"))
        reDoc(ReDocOptions("/redoc"))
        defaultDocumentation { doc ->
            doc.json("500", ErrorResponse::class.java)
            doc.json("503", ErrorResponse::class.java)
        }
    }
)

