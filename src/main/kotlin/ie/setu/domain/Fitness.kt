package ie.setu.domain

import org.joda.time.DateTime


data class Fitness (
    var id: Int,
    var dayType: String,
    var started: DateTime,
    var userId: Int)

