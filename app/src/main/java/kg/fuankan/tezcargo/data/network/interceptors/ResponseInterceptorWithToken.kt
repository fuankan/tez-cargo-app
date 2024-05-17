package kg.fuankan.tezcargo.data.network.interceptors

import kg.fuankan.tezcargo.data.local.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class ResponseInterceptorWithToken @Inject constructor(
    private val prefs: AppPreferences
) : BaseResponseInterceptor() {

    private val lock by lazy { AtomicReference<Any>() }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        val bodyString = response.body?.string()
        val contentType = response.body?.contentType()

        val isTokenInvalid =
            bodyString != null && bodyString.contains("Could not find any token")
        if (isTokenInvalid) {
            synchronized(lock) {
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
