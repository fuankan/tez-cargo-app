package kg.fuankan.tezcargo.data.network.interceptors

import androidx.annotation.WorkerThread
import kg.fuankan.tezcargo.data.local.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val prefs: AppPreferences
): Interceptor {

    @WorkerThread
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.run {
            addHeader("Authorization", " Bearer " + prefs.token)
        }
        return chain.proceed(requestBuilder.build())
    }
}