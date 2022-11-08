package ie.setu
import ie.setu.config.DbConfig
import ie.setu.config.JavalinConfig


fun main() {
    // now on post interim work will be done

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()
}