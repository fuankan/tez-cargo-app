package kg.fuankan.tezcargo.data.repo

import kg.fuankan.tezcargo.data.models.ApiResponse
import kg.fuankan.tezcargo.data.models.CargoCreateEdit
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.data.models.DeliveryStatus
import kg.fuankan.tezcargo.data.network.api.DeliveryApi
import kg.fuankan.tezcargo.utils.Dispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeliveryRepo @Inject constructor(
    private val dispatcher: Dispatcher,
    private val deliveryApi: DeliveryApi
) {

    suspend fun createDelivery(requestModel: CargoCreateEdit) = withContext(dispatcher.io()) {
        deliveryApi.createDelivery(requestModel)
    }

    suspend fun editDelivery(deliveryId: Int, requestModel: CargoCreateEdit) = withContext(dispatcher.io()) {
        deliveryApi.editDelivery(deliveryId, requestModel)
    }

    suspend fun getDeliveryById(deliveryId: Int) = withContext(dispatcher.io()) {
        deliveryApi.getDeliveryById(deliveryId)
    }

    suspend fun changeDeliveryStatus(deliveryId: Int, status: DeliveryStatus) =
        withContext(dispatcher.io()) {
            deliveryApi.changeDeliveryStatus(deliveryId, status)
        }

    suspend fun getFilteredDeliveries(cargoFilter: CargoFilter) = withContext(dispatcher.io()) {
        deliveryApi.getFilteredDeliveries(cargoFilter)
    }

    suspend fun getStorages() = withContext(dispatcher.io()) {
        deliveryApi.getStorages()
    }

    suspend fun getStorageInfoById(storageId: Int) = withContext(dispatcher.io()) {
        deliveryApi.getStorageInfoById(storageId)
    }

    suspend fun verifyDriver(driverId: Int, isVerified: Boolean) = withContext(dispatcher.io()) {
        deliveryApi.verifyDriver(driverId, isVerified)
    }

    suspend fun getDriverRequests() = withContext(dispatcher.io()) {
        deliveryApi.getDriverRequests()
    }

    suspend fun getAcceptedDrivers() = withContext(dispatcher.io()) {
        deliveryApi.getAcceptedDrivers()
    }

    suspend fun getAccounting(type: String) = withContext(dispatcher.io()) {
        deliveryApi.getAccounting(type)
    }
}