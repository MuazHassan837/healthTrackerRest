package ie.setu.domain

import org.joda.time.DateTime

/**
 * This class is used to define the activity
 */

data class Activity (var id: Int,
                     var description:String,
                     var duration: Double,
                     var calories: Int,
                     var started: DateTime,
                     var userId: Int)