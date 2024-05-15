package kg.fuankan.tezcargo.data.models

data class ApiResponse<T>(
    val data: T?,
    val code: Int?,
    val message: String?
)

enum class ResultCode{
    SUCCESS,
    FAIL
}