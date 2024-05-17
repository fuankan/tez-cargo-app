package kg.fuankan.tezcargo.extensions

import android.app.AlertDialog
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

fun Date.formatByRegex(regex: String): String {
    return SimpleDateFormat(regex, Locale.getDefault()).format(this)
}

fun String.toDate(pattern: String): Date {
    val format = SimpleDateFormat(pattern)
    return format.parse(this)
}

fun String.changeFormat(fromFormat: String, toFormat: String = "dd.MM.yyyy"): String {
    return (this.toDate(fromFormat)).formatByRegex(toFormat)
}

fun String.toCalendarOrNow(): Calendar {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(this)
        Calendar.getInstance().apply {
            time = date
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Calendar.getInstance()
    }
}