package ie.setu.domain

import org.joda.time.DateTime

data class Mood (
    var id: Int,
    var mood:String,
    var userId: Int,
    var started: DateTime)
