package ie.setu.domain

import org.joda.time.DateTime

/**
 * This class is used to define the hydration model
 */
data class InTake (
    var id: Int,
    var amountltr: Double,
    var substance:String,
    var userId: Int,
    var started: DateTime)
