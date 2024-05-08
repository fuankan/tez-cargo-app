package kg.fuankan.tezcargo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.fuankan.tezcargo.BuildConfig
import kg.fuankan.tezcargo.annotations.AuthCheckOkHttpClient
import kg.fuankan.tezcargo.annotations.AuthRetrofit
import kg.fuankan.tezcargo.annotations.InterceptedWithTokenCheckerOkHttpClient
import kg.fuankan.tezcargo.annotations.InterceptedWithTokenCheckerRetrofit
import kg.fuankan.tezcargo.data.network.api.AuthApi
import kg.fuankan.tezcargo.data.network.interceptors.RequestInterceptor
import kg.fuankan.tezcargo.data.network.interceptors.ResponseConverterFactory
import kg.fuankan.tezcargo.data.network.interceptors.ResponseInterceptor
import kg.fuankan.tezcargo.data.network.interceptors.ResponseInterceptorWithToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @AuthCheckOkHttpClient
    @Singleton
    fun provideNoInterceptorHttpClient(
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = createOkHttpClientBuilder(responseInterceptor).build()

    @Provides
    @InterceptedWithTokenCheckerOkHttpClient
    @Singleton
    fun provideInterceptedHttpClient(
        requestInterceptor: RequestInterceptor,
        responseInterceptorWithToken: ResponseInterceptorWithToken
    ): OkHttpClient =
        createOkHttpClientBuilder(requestInterceptor, responseInterceptorWithToken).build()

    @Provides
    @AuthRetrofit
    @Singleton
    fun provideNoInterceptorRetrofit(
        @AuthCheckOkHttpClient httpClient: OkHttpClient,
        converter: ResponseConverterFactory
    ) = createRetrofit(httpClient, converter)

    @Provides
    @InterceptedWithTokenCheckerRetrofit
    @Singleton
    fun provideInterceptedRetrofit(
        @InterceptedWithTokenCheckerOkHttpClient httpClient: OkHttpClient,
        converter: ResponseConverterFactory
    ) = createRetrofit(httpClient, converter)

    private fun createOkHttpClientBuilder(vararg interceptors: Interceptor): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        return OkHttpClient.Builder()
            .apply {
                connectTimeout(1, TimeUnit.MINUTES)
                writeTimeout(1, TimeUnit.MINUTES)
                readTimeout(1, TimeUnit.MINUTES)

                interceptors.forEach {
                    addInterceptor(it)
                }
                if (BuildConfig.DEBUG) {
                    addInterceptor(interceptor)
                }
            }
    }

    private fun createRetrofit(
        httpClient: OkHttpClient,
        converter: ResponseConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converter)
            .client(httpClient)
            .build()
    }
}