package kg.fuankan.tezcargo.data.network.interceptors

import android.util.Log
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import kg.fuankan.tezcargo.BuildConfig
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.models.ApiResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TokenRefresher @Inject constructor(private val prefs: AppPreferences) {
    val lock by lazy { AtomicReference<Any>() }

    fun refresh(): Boolean{
        val refreshToken = prefs.refreshToken
        val url = BuildConfig.BASE_URL + "/refresh-token"

        val refreshUrl = URL(url)
        val urlConnection = refreshUrl.openConnection() as HttpURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.setRequestProperty("Content-Type", "application/json")
        urlConnection.setRequestProperty("refreshToken", refreshToken)
        urlConnection.useCaches = false

        val responseCode = urlConnection.responseCode
        return if (responseCode == 200) {
            val `in` = BufferedReader(InputStreamReader(urlConnection.inputStream) as Reader)
            var inputLine: String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
            val customResponse: ApiResponse<*>? = Gson().fromJson(response.toString(), ApiResponse::class.java )
            val map = customResponse?.data as LinkedTreeMap<String, String>

            prefs.token = map["token"]
            prefs.refreshToken = map["refreshToken"]
            true
        } else {
            Log.i("REFTOK", "refreshToken: failed ")
            val `in` =
                BufferedReader(InputStreamReader(urlConnection.errorStream))
            var inputLine: String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
            Log.i("REFTOK", "refreshToken: failed 303>>$response")
            false
        }
    }
}