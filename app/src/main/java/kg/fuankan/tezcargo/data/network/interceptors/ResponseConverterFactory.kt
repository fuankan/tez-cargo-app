package kg.fuankan.tezcargo.data.network.interceptors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.fuankan.tezcargo.data.models.ApiResponse
import kg.fuankan.tezcargo.data.network.util.ApiException
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ResponseConverterFactory @Inject constructor() : Converter.Factory() {

    private val gson: Gson = Gson()
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = ApiResponse::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)

        return ResponseBodyConverter(gson, gsonConverter as Converter<ResponseBody, ApiResponse<Any?>>, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    inner class ResponseBodyConverter<T>(
        private val gson: Gson,
        private val converter: Converter<ResponseBody, ApiResponse<T?>>,
        private val type: Type
    ) : Converter<ResponseBody, T?> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): T? {
            val responseStr = responseBody.string()
            val contentType = responseBody.contentType()

            val apiResponse = converter.convert(responseStr.toResponseBody(contentType))
            if (apiResponse?.code != null && apiResponse.code != 200) {
                throw ApiException(apiResponse.message ?: "Unknown error")
            }

            return if(apiResponse?.data != null) {
                apiResponse.data
            } else {
                val directConverter = gson.fromJson<T>(responseStr, TypeToken.get(type).type)
                directConverter
            }
        }
    }
}