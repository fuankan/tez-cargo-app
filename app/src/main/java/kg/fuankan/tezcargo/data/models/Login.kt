package kg.fuankan.tezcargo.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse (
    val userId: Int,
    val token: String,
    val role: Role
)
