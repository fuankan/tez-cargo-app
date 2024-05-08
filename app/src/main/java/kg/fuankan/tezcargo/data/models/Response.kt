package kg.fuankan.tezcargo.data.models

data class Response<T>(
    var result: T? = null,
    var code: Int? = null,
    var message: String? = null
)

enum class ResultCode{
    SUCCESS,
    FAIL
}