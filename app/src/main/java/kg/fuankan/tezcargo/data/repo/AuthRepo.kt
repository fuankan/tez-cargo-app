package kg.fuankan.tezcargo.data.repo

import kg.fuankan.tezcargo.data.models.LoginRequest
import kg.fuankan.tezcargo.data.network.api.AuthApi
import kg.fuankan.tezcargo.utils.Dispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val dispatcher: Dispatcher,
    private val authApi: AuthApi
) {

    suspend fun login(username: String, password: String) = withContext(dispatcher.io()) {
        authApi.login(
            LoginRequest(
                username = username,
                password = password
            )
        )
    }

    suspend fun resetPassword(email: String) = withContext(dispatcher.io()) {
        authApi.resetPassword(email)
    }

    suspend fun checkOtp(email: String, otp: String) = withContext(dispatcher.io()) {
        authApi.checkOtp(email, otp)
    }

    suspend fun changePassword(email: String, password: String) = withContext(dispatcher.io()) {
        authApi.changePassword(email, password)
    }

    suspend fun getUserData(id: Int) = withContext(dispatcher.io()) {
        authApi.getUserData(id)
    }
}