package kg.fuankan.tezcargo.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Context.showToast(stringResId: Int, length: Int = Toast.LENGTH_LONG) {
    showToast(getString(stringResId), length)
}