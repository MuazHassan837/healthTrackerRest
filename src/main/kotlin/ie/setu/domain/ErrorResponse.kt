package ie.setu.domain

/**
 * This class is used to define the error response
 */
data class ErrorResponse(
    val title: String,
    val status: Int,
    val type: String,
    val details: Map<String, String>?
)