package kg.fuankan.tezcargo.data.repo

import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.models.LoginRequest
import kg.fuankan.tezcargo.data.network.api.AuthApi
import kg.fuankan.tezcargo.utils.Dispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val dispatcher: Dispatcher,
    private val authApi: AuthApi,
    private val prefs: AppPreferences
) {

    suspend fun login(username: String, password: String) = withContext(dispatcher.io()) {
        authApi.login(
            LoginRequest(
                username = username,
                password = password
            )
        )
    }
}