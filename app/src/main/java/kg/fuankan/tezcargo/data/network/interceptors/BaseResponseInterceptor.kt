package kg.fuankan.tezcargo.data.network.interceptors

import android.content.Context
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.data.network.util.PasswordMismatchException
import kg.fuankan.tezcargo.data.network.util.UserNotAllowedException
import kg.fuankan.tezcargo.data.network.util.UserNotFoundException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

abstract class BaseResponseInterceptor: Interceptor {

    @Inject protected lateinit var context: Context

    protected fun checkForCustomServerException(response: Response, bodyString: String?) {
        if (response.code == 400 && !bodyString.isNullOrEmpty()) {
            when {
                bodyString.contains("Password verify mismatch") ->
                    throw PasswordMismatchException()
                bodyString.contains("could not find any user") ->
                    throw UserNotFoundException("Could not find any user")
                bodyString.contains("Admin-user not allowed") ->
                    throw UserNotAllowedException("Admin-user not allowed")
                else -> {
                }
            }
        }
    }
}