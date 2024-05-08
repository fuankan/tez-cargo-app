package kg.fuankan.tezcargo.data.network.interceptors

import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.network.util.ServerErrorHandler
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ResponseInterceptorWithToken @Inject constructor(
    private val tokenRefresher: TokenRefresher,
    private val prefs: AppPreferences,
    private val errorHandler: ServerErrorHandler
) : BaseResponseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        val bodyString = response.body?.string()
        val contentType = response.body?.contentType()

        val isTokenInvalid =
            bodyString != null && bodyString.contains("Could not find any token")
        if (isTokenInvalid) {
            synchronized(tokenRefresher.lock) {
//                val result: Boolean = tokenRefresher.refresh()
                /*if (result) {
                    val newRequest = request.newBuilder()
                        .header("Authorization", prefs.token ?: "")
                        .build()
                    return chain.proceed(newRequest)
                } else {
                    errorHandler.onTokenExpire()
                }*/

                val newRequest = request.newBuilder()
                    .header("Authorization", prefs.token ?: "")
                    .build()
                return chain.proceed(newRequest)
            }
        }
        checkForCustomServerException(response, bodyString)
        return response.newBuilder().body( bodyString!!.toResponseBody(contentType)).build()
    }
}
