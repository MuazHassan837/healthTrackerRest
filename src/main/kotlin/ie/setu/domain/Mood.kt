package ie.setu.domain

import org.joda.time.DateTime

/**
 * This class is used to define the mood model
 */
data class Mood (
    var id: Int,
    var mood:String,
    var userId: Int,
    var started: DateTime)
