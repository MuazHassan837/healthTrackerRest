package ie.setu.utils
import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

/**
 * This class is used to define the utilities(mapping) for the model
 */
fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)

fun mapToInTake(it: ResultRow) = InTake (
    id = it[InTakes.id],
    amountltr = it[InTakes.amountltr],
    substance = it[InTakes.substance],
    userId = it[InTakes.userId],
    started = it[InTakes.started]
    )

fun mapToFitness(it: ResultRow) = Fitness (
    id = it[Finesse.id],
    dayType = it[Finesse.dayType],
    started = it[Finesse.started],
    userId = it[Finesse.userId]
)


fun mapToMood(it: ResultRow) = Mood (
    id = it[Moods.id],
    mood = it[Moods.mood],
    userId = it[Moods.userId],
    started = it[Moods.started])
