package kg.fuankan.tezcargo.data.models

data class ApiResponse(
    val code: Int?,
    val message: String?
)

enum class ResultCode{
    SUCCESS,
    FAIL
}