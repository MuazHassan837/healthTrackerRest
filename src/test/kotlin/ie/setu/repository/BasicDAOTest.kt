package ie.setu.repository

import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll

open class BasicDAOTest {

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
}