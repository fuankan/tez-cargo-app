package kg.fuankan.tezcargo.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ResponseInterceptor @Inject constructor(): BaseResponseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val bodyString = response.body?.string()
        val contentType = response.body?.contentType()

        checkForCustomServerException(response, bodyString)
        return response.newBuilder().body(bodyString!!.toResponseBody(contentType)).build()
    }
}