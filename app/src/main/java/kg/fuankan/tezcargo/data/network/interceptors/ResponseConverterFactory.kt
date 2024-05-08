package kg.fuankan.tezcargo.data.network.interceptors

import com.google.gson.Gson
import kg.fuankan.tezcargo.data.models.Response
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

    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(Gson())

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = Response::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)

        return ResponseBodyConverter(gsonConverter as Converter<ResponseBody, Response<Any?>>)
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

    inner class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, Response<T?>>) :
        Converter<ResponseBody, T?> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): T? {
            val responseStr = responseBody.string()
            val contentType = responseBody.contentType()
            val response = converter.convert(responseStr.toResponseBody(contentType))

            return if(response?.result == null) null else response.result as T
        }
    }
}