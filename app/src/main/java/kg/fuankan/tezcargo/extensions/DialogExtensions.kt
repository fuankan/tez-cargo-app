package kg.fuankan.tezcargo.extensions

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kg.fuankan.tezcargo.R
import java.util.Locale

fun AlertDialog.Builder.positiveButton(@StringRes btnTextId: Int, handleClick: () -> Unit = {}) {
    this.setPositiveButton(context.getString(btnTextId)
        .uppercase(Locale.getDefault())) { dialogInterface, _ ->
        handleClick()
        dialogInterface.dismiss()
    }
}

fun AlertDialog.Builder.negativeButton(@StringRes btnTextId: Int, handleClick: () -> Unit = {}) {
    this.setNegativeButton(context.getString(btnTextId)
        .uppercase(Locale.getDefault())) { dialogInterface, _ ->
        handleClick()
        dialogInterface.dismiss()
    }
}

private fun Context.showDialog(lifecycle: Lifecycle, builderFunction: AlertDialog.Builder.() -> Any) {
    val builder = AlertDialog.Builder(this, com.design2.chili2.R.style.Chili_AlertDialog).apply {
        setCancelable(false)
    }
    builder.builderFunction()
    val dialog = builder.create()
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun dismissDialog() {
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }
    })
    dialog.show()
}

fun Fragment.showDialog(builderFunction: AlertDialog.Builder.() -> Any) {
    context?.showDialog(lifecycle, builderFunction)
}

fun AppCompatActivity.showDialog(builderFunction: AlertDialog.Builder.() -> Any) {
    this.showDialog(lifecycle, builderFunction)
}