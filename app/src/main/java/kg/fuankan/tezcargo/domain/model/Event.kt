package kg.fuankan.tezcargo.domain.model

import androidx.annotation.StringRes

sealed class Event {
    object Success : Event()
    object Fail : Event()
    class NotificationRes(@StringRes val stringRes: Int) : Event()
    class Notification(val text: String) : Event()
}

sealed class AuthEvent: Event() {
    object LoginSuccess : AuthEvent()
    object RegisterSuccess : AuthEvent()
}