package ie.setu.domain

import org.joda.time.DateTime

data class InTake (
    var id: Int,
    var amountltr: Double,
    var substance:String,
    var userId: Int,
    var started: DateTime)
