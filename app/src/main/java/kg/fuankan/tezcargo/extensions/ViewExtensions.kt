package kg.fuankan.tezcargo.extensions

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes

val TextView.string: String
    get() {
        return this.text.toString()
    }

fun TextView.isEmpty(): Boolean {
    return this.text.isEmpty()
}

fun View.trySetMargins(top: Float? = null, right: Float? = null, bottom: Float? = null, left: Float? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let { params ->
        top?.let { params.topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, resources.displayMetrics).toInt() }
        right?.let { params.rightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, resources.displayMetrics).toInt() }
        bottom?.let { params.bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, resources.displayMetrics).toInt() }
        left?.let { params.leftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, resources.displayMetrics).toInt() }
        layoutParams = params
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setBottomMargin(margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, margin)
    layoutParams = params
}

fun View.setTopMargin(margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, margin, params.rightMargin, params.bottomMargin)
    layoutParams = params
}

fun View.setRightMargin(margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, params.topMargin, margin, params.bottomMargin)
    layoutParams = params
}

fun View.setLeftMargin(margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(margin, params.topMargin, params.rightMargin, params.bottomMargin)
    layoutParams = params
}

fun View.setMargin(@DimenRes margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    val marginDp = resources.getDimension(margin).toInt()
    params.setMargins(marginDp, marginDp, marginDp, marginDp)
    layoutParams = params
}

fun ViewGroup.inflate( @LayoutRes resid: Int): View{
    return LayoutInflater.from(context)
        .inflate(resid,this, false)
}