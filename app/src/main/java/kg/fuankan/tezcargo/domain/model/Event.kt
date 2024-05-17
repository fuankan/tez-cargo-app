package kg.fuankan.tezcargo.domain.model

import androidx.annotation.StringRes
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.data.models.CargoFilter

sealed class Event {
    object Success : Event()
    object Fail : Event()
    class NotificationRes(@StringRes val stringRes: Int) : Event()
    class Notification(val text: String) : Event()
}

sealed class AuthEvent: Event() {
    object LoginSuccess : AuthEvent()
    object Logout : AuthEvent()
    class OtpSent(val note: String) : AuthEvent()
    class OtpChecked(val note: String) : AuthEvent()
    class PasswordChanged(val note: String) : AuthEvent()
}

sealed class DeliveryEvent: Event() {
    class DeliveryCreated(val note: String? = null) : DeliveryEvent()
    class DeliveryUpdated(val note: String? = null) : DeliveryEvent()
    class DeliveryFiltered(val filters: CargoFilter? = null) : DeliveryEvent()
    class FilteredDeliveryList(val list: List<CargoDesc>? = null) : DeliveryEvent()
}