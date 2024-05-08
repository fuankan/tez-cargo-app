package kg.fuankan.tezcargo.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.*

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    var token: String?
        get() {
            return prefs.getString(TOKEN, "")
        }
        set(value) {
            prefs.edit {
                putString(TOKEN, value)
            }
        }

    var refreshToken: String?
        get() {
            return prefs.getString(REFRESH_TOKEN, "")
        }
        set(value) {
            prefs.edit {
                putString(REFRESH_TOKEN, value)
            }
        }

    var role: String?
        get() {
            return prefs.getString(ROLE, "")
        }
        set(value) {
            prefs.edit {
                putString(ROLE, value)
            }
        }

    fun clear() {
        prefs.edit {
            clear()
        }
    }

    companion object {
        const val PREF_NAME = "kg.fuankan.tezcargo"
        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
        const val ROLE = "role"

        @SuppressLint("ConstantLocale")
        private val dateFormat = SimpleDateFormat("dd:MM:yyyy HH:mm:ss", Locale("ru"))
    }
}