package kg.fuankan.tezcargo.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ResponseInterceptor @Inject constructor() : BaseResponseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.body
        val bodyString = responseBody?.string()
        val contentType = responseBody?.contentType()

        checkForCustomServerException(response, bodyString)

        val newResponseBody = bodyString?.toResponseBody(contentType) ?: responseBody

        return response.newBuilder().body(newResponseBody).build()
    }
}
