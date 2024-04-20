package kg.fuankan.tezcargo.data.network.util

import retrofit2.HttpException

interface ServerErrorHandler {
    fun onNoInternetConnectionError()
    fun onUnknownError(e: Throwable)
    fun onTimeOutError()
    fun onHttpError(e: HttpException)
    fun onCustomServerError(e: CustomServerException)
    fun onTokenExpire()
}