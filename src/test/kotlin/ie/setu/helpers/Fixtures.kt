package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Users
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.repository.user1
import ie.setu.repository.user2
import ie.setu.repository.user3
import ie.setu.repository.user4
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validID = 201
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"

val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")

val users = arrayListOf<User>(
    User(name = "Muaz Hassan", email = "syedmuaz@wit.ie", id = 201),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)

val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 201),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 201),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 201)
)

internal fun populateThreeUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(user1)
    userDAO.save(user2)
    userDAO.save(user3)
    return userDAO
}

internal fun populateFourUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(user1)
    userDAO.save(user2)
    userDAO.save(user3)
    userDAO.save(user4)

    return userDAO
}

fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities[0])
    activityDAO.save(activities[1])
    activityDAO.save(activities[2])
    return activityDAO
}