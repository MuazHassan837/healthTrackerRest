package ie.setu.domain

import org.joda.time.DateTime

/**
 * This class is used to define the fitness model
 */
data class Fitness (
    var id: Int,
    var dayType: String,
    var started: DateTime,
    var userId: Int)

