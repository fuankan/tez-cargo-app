package kg.fuankan.tezcargo.data.models

data class LoginRequest (
    val username: String,
    val password: String
)

data class LoginResponse (
    val userId: Int,
    val token: String,
    val role: Role
)

data class UserData(
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null
)