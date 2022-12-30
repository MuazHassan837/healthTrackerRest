package ie.setu.config
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import org.jetbrains.exposed.sql.Database


/**
 * This class is used to connect to the database
 */

class DbConfig{
    fun getDbConnection() :Database{
        val PGUSER = "fpppevzp"
        val PGPASSWORD = "UxNHb6s4pMlmFT1b4q8y_l5T6GGVxUk_"
        val PGHOST = "lucky.db.elephantsql.com"
        val PGPORT = "5432"
        val PGDATABASE = "fpppevzp"

        val url = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"
        val dbConfig = Database.connect(url,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )

        logger.info{"db url - connection: " + dbConfig.url}

        return dbConfig
    }
}