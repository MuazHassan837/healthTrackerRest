package ie.setu.helpers

import ie.setu.config.JavalinConfig

/**
 * This section is for sharing javalin instance across the test classes
 */
object ServerContainer {

    val instance by lazy {
        startServerContainer()
    }

    private fun startServerContainer() = JavalinConfig().startJavalinService()

}