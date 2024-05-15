package kg.fuankan.tezcargo.data.network.api

import kg.fuankan.tezcargo.data.models.LoginRequest
import kg.fuankan.tezcargo.data.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}