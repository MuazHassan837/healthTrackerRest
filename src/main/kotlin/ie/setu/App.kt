package ie.setu
import ie.setu.config.DbConfig
import ie.setu.config.JavalinConfig

/**
 * This class is used to start the application
 */
fun main() {
    // now on post interim work will be done

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()
}