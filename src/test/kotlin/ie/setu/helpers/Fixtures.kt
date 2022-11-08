package ie.setu.helpers

import ie.setu.domain.*
import ie.setu.domain.db.*
import ie.setu.domain.repository.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

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
val InValidNo = Int.MIN_VALUE

val updateMood = "Much Wow"
val updatedFitType = "LegDay"

val updatedAmount = 1.25
val updatedSubstance = "Juice"

val users = arrayListOf<User>(
    User(name = "Muaz Hassan", email = "syedmuaz@wit.ie", id = 201),
    User(name = "Not Muaz", email = "hehe@wow.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)

val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 201),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 201),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 201)
)
val intakes = arrayListOf<InTake>(
    InTake(id = 1, amountltr = 1.5, substance = "water", userId = 201),
    InTake(id = 2, amountltr = 0.5, substance = "milk", userId = 201)
    )


val fitnessObjs = arrayListOf<Fitness>(
    Fitness(id = 1, dayType = "Chest Day", started = DateTime.now(), userId = 201),
    Fitness(id = 2, dayType = "leg Day", started = DateTime.now(), userId = 2),
)

val moodObjs = arrayListOf<Mood>(
    Mood(id = 1, mood = "Happy", userId = 201),
    Mood(id = 2, mood = "Sad", userId = 2)
    )

internal fun populateUserForMood(): UserDAO{
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    return userDAO
}

internal fun populateMoodTable(): MoodDAO {
    SchemaUtils.create(Moods)
    val moodDAO = MoodDAO()
    moodDAO.save(moodObjs.get(0))
    moodDAO.save(moodObjs.get(1))
    return moodDAO
}

internal fun favUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    return userDAO
}

internal fun populateUserForFitness(): UserDAO{
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    return userDAO
}
internal  fun populateFitnessTable(): FitnessDAO {
    SchemaUtils.create(Finesse)
    val fitnessDAO = FitnessDAO()
    fitnessDAO.save(fitnessObjs.get(0))
    fitnessDAO.save(fitnessObjs.get(1))
    return fitnessDAO
}

internal  fun populateInTakeTable() : InTakeDAO {
    SchemaUtils.create(InTakes)
    val intakeDAO = InTakeDAO()
    intakeDAO.save(intakes.get(0))
    intakeDAO.save(intakes.get(1))
    return intakeDAO
}
internal fun populateThreeUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    userDAO.save(users.get(2))
    return userDAO
}

internal fun populateFourUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    userDAO.save(users.get(2))
    userDAO.save(users.get(3))

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
