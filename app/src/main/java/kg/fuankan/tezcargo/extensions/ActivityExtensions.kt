package kg.fuankan.tezcargo.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> AppCompatActivity.collectFlow(flow: Flow<T?>, call: suspend (emission: T?) -> Unit){
    this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect {
                call(it)
            }
        }
    }
}

fun Activity.finishWithResult(data: Intent? = null) {
    setResult(AppCompatActivity.RESULT_OK, data)
    finish()
}