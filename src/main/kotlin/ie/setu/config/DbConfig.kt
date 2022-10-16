package ie.setu.config

import org.jetbrains.exposed.sql.Database

class DbConfig{

    fun getDbConnection() :Database{
        return Database.connect(
            "jdbc:postgresql://ec2-3-224-8-189.compute-1.amazonaws.com:5432/dciecsop9967be?sslmode=require",
            driver = "org.postgresql.Driver",
            user = "tilzrkkjjbgqec",
            password = "db1728c3933cf730e31ec76a47722b39148e758648388e766d9b0771dae94225")
    }

}