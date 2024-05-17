package kg.fuankan.tezcargo.data.network.interceptors

import android.content.Context
import com.google.gson.JsonParser
import kg.fuankan.tezcargo.data.network.util.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

abstract class BaseResponseInterceptor : Interceptor {

    @Inject
    protected lateinit var context: Context

    protected fun checkForCustomServerException(response: Response, bodyString: String?) {
        if(response.code == 500) {
            throw ApiException("Server error")
        } else if (response.code != 200) {
            val jsonElement = JsonParser.parseString(bodyString)
            if (jsonElement.isJsonObject) {
                val jsonObject = jsonElement.asJsonObject
                if (jsonObject.has("code") || jsonObject.has("message")) {
                    val message = jsonObject.get("message").asString
                    throw ApiException(message)
                }
            }
        }
    }
}