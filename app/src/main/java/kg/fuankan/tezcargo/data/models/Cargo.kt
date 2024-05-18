package kg.fuankan.tezcargo.data.models

import java.io.Serializable

data class CargoDesc(
    var deliveryId: Int,
    var price: String? = null,
    var range: String? = null,
    var loadingDate: String? = null,
    var unloadingDate: String? = null,
    var transitRouteDays: Int? = null,
    var deliveryFactAddress: String? = null,
    var deliveryCity: String? = null,
    var deliveryState: String? = null,
    var deliveryCountry: String? = null,
    var deliveryPhoneNumber: String? = null,
    var storageId: Int? = null,
    var storageName: String? = null,
    var storageFactAddress: String? = null,
    var storageGeoLocation: String? = null,
    var storagePhoneNumber: String? = null,
    var driverName: String? = null,
    var driverPhoneNumber: String? = null,
    var deliveryStatus: DeliveryStatus? = null
) : Serializable

data class CargoFilter(
    var driverName: String? = null,
    var companyName: String? = null,
    var filialName: String? = null,
    var loadingDate: String? = null,
    var unloadingDate: String? = null,
    var rangeFrom: Int? = null,
    var rangeTill: Int? = null,
    var priceFrom: Int? = null,
    var priceTill: Int? = null,
    var statuses: List<DeliveryStatus>? = null
) : Serializable

data class CargoCreateEdit(
    var price: Int? = null,
    var range: Int? = null,
    var loadingDate: String? = null,
    var unloadingDate: String? = null,
    var factAddress: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var phoneNumber: String? = null,
    var storageId: Int? = null
): Serializable

data class StorageOption(
    val id: Int,
    val name: String
) : Serializable

data class StorageInfo(
    val storageId: Int,
    val storageName: String,
    val storageFactAddress: String,
    val storageGeoLocation: String,
    val storagePhoneNumber: String
): Serializable
