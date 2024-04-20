package kg.fuankan.tezcargo.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(flow: Flow<T?>, call: suspend (T?) -> Unit, needCleanUp: Boolean = true){
    this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED){
            flow.collect {
                it?.let {
                    call(it)
                    if(needCleanUp){
                        when(flow){
                            is StateFlow -> (flow as MutableStateFlow).value = null
                        }
                    }
                }
            }
        }
    }
}
