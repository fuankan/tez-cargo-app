package kg.fuankan.tezcargo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.fuankan.tezcargo.data.network.util.CustomServerException
import kg.fuankan.tezcargo.data.network.util.ServerErrorHandler
import kg.fuankan.tezcargo.domain.model.Event
import kg.fuankan.tezcargo.utils.Dispatcher
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseVM @Inject constructor() : ViewModel() {

    @Inject lateinit var serverErrorHandler: ServerErrorHandler
    @Inject lateinit var dispatcher: Dispatcher

    private val _event: MutableStateFlow<Event?> by lazy {
        MutableStateFlow(null)
    }
    val event: StateFlow<Event?>
    get() = _event

    private val _isLoading: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
    get() = _isLoading

    fun triggerEvent(event: Event?){
        _event.value = event
    }

    protected open fun hideLoading(){
        _isLoading.value = false
    }

    protected open fun showLoading(){
        _isLoading.value = true
    }

    fun launchWithErrorHandling(
        call: suspend () -> Unit,
        finally: (suspend () -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            try {
                call()
            } catch (e: Throwable) {
                when (e) {
                    is SocketTimeoutException -> serverErrorHandler.onTimeOutError()
                    is UnknownHostException -> serverErrorHandler.onNoInternetConnectionError()
                    is CancellationException -> { } /**thrown when coroutine gets cancelled*/
                    else -> handleError(e)
                }
            } finally {
                hideLoading()
                finally?.invoke()
            }
        }
    }

    /** This method can be overridden if more sophisticated actions are needed */
    open fun handleError(e: Throwable) {
        when (e) {
            is HttpException -> serverErrorHandler.onHttpError(e)
            is CustomServerException -> serverErrorHandler.onCustomServerError(e)
            else ->  serverErrorHandler.onUnknownError(e)
        }
    }
}