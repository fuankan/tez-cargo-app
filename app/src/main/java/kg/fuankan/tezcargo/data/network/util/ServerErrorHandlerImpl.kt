package kg.fuankan.tezcargo.data.network.util

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.ui.auth.AuthActivity
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.extensions.showToast
import retrofit2.HttpException

class ServerErrorHandlerImpl(
    @ApplicationContext private val context: Context,
    private val appPreferences: AppPreferences
) : ServerErrorHandler {

    override fun onNoInternetConnectionError() {
        context.showToast(context.getString(R.string.error_no_internet))
    }

    override fun onUnknownError(e: Throwable) {
        context.showToast(context.getString(R.string.error_unknown))
    }

    override fun onTimeOutError() {
        context.showToast(context.getString(R.string.error_timeout))
    }

    override fun onHttpError(e: HttpException) {
        context.showToast(e.response()?.body()?.toString() ?: e.code().toString())
    }

    override fun onCustomServerError(e: CustomServerException) {
        var errorMessage = e.description ?: context.getString(R.string.error_unknown)
        if (!errorMessage.isNullOrEmpty() && e.errorCode != null) {
            errorMessage += " Код: ${e.errorCode}"
        }
        context.showToast(errorMessage)
    }

    override fun onTokenExpire() {
        clearPreferences()
        startAuthScreen()
    }

    private fun startAuthScreen() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun clearPreferences() {
        with(appPreferences) {
            token = null
            refreshToken = null
        }
    }
}