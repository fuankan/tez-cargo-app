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
    var deliveryGeoLocation: String? = null,
    var deliveryPhoneNumber: String? = null,
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
    var price: Int,
    var range: Int,
    var loadingDate: String,
    var unloadingDate: String,
    var factAddress: String,
    var city: String,
    var state: String,
    var country: String,
    var phoneNumber: String,
    var storageId: Int
): Serializable

data class Storage(
    val id: Int,
    val name: String
) : Serializable
