package kg.fuankan.tezcargo.domain.model

import androidx.annotation.StringRes
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.data.models.DriverInfo
import kg.fuankan.tezcargo.data.models.StorageInfo
import kg.fuankan.tezcargo.data.models.StorageOption

sealed class Event {
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
    class FilteredDeliveryList(val list: List<CargoDesc>? = null) : DeliveryEvent()
    class StoragesOptionList(val list: List<StorageOption>? = null) : DeliveryEvent()
    class DeliveryById(val cargoDesc: CargoDesc) : DeliveryEvent()
    class StorageInfoFetched(val storageInfo: StorageInfo? = null) : DeliveryEvent()
    class DeliveryStatusChanged(val note: String? = null) : DeliveryEvent()
    class DriverVerified(val note: String? = null) : DeliveryEvent()
    class DriverListFetched(val list: List<DriverInfo>? = null) : DeliveryEvent()
    class AccountingFetched(val url: String? = null) : DeliveryEvent()
}