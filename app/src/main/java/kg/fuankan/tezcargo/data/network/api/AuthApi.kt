package kg.fuankan.tezcargo.data.network.api

import kg.fuankan.tezcargo.data.models.ApiResponse
import kg.fuankan.tezcargo.data.models.LoginRequest
import kg.fuankan.tezcargo.data.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/api/v1/auth/forgot-password")
    suspend fun resetPassword(@Query("email") email: String) : ApiResponse

    @GET("/api/v1/auth/check-otp")
    suspend fun checkOtp(@Query("email") email: String, @Query("otpValue") otp: String): ApiResponse

    @GET("/api/v1/auth/change-password")
    suspend fun changePassword(@Query("email") email: String, @Query("password") password: String): ApiResponse
}