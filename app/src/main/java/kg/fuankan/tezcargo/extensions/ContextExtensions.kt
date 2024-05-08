package kg.fuankan.tezcargo.extensions

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import java.util.Calendar

fun Context.showToast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun Context.showToast(stringResId: Int, length: Int = Toast.LENGTH_LONG) {
    showToast(getString(stringResId), length)
}

fun Context.showDatePickerDialog(onDateSet: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
        onDateSet(selectedYear, selectedMonth, selectedDay)
    }, year, month, day).show()
}