package kg.fuankan.tezcargo.ui.cargo.desc

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.models.CargoCreateEdit
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.data.models.DeliveryStatus
import kg.fuankan.tezcargo.data.repo.DeliveryRepo
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CargoDescVM @Inject constructor(
    private val deliveryRepo: DeliveryRepo
) : BaseVM() {

    var cargoDescId: Int? = null
    private var originalStatus: DeliveryStatus? = null

    private val _cargoDescFlow = MutableStateFlow<CargoDesc?>(null)

    private fun setCargoDesc(cargoDesc: CargoDesc) {
        cargoDescId = cargoDesc.deliveryId
        originalStatus = cargoDesc.deliveryStatus
        _cargoDescFlow.value = cargoDesc
    }

    val cargoDesc: CargoDesc?
        get() = _cargoDescFlow.value

    fun clearViewModel() {
        cargoDescId = null
        originalStatus = null
        _cargoDescFlow.value = null
    }

    fun getDeliveryById(deliveryId: Int) {
        showLoading()
        launchWithErrorHandling({
            deliveryRepo.getDeliveryById(deliveryId).also {
                setCargoDesc(it)
                triggerEvent(DeliveryEvent.DeliveryById(it))
            }
        })
    }

    fun getStorageInfoById(storageId: Int) {
        showLoading()
        launchWithErrorHandling({
            deliveryRepo.getStorageInfoById(storageId).also { storageInfo ->
                triggerEvent(DeliveryEvent.StorageInfoFetched(storageInfo))
            }
        })
    }

    fun getStoragesOptionList() {
        showLoading()
        launchWithErrorHandling({
            deliveryRepo.getStorages().also {
                triggerEvent(DeliveryEvent.StoragesOptionList(it))
            }
        })
    }

    fun updatePrice(newPrice: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(price = newPrice)
    }

    fun updateRange(newRange: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(range = newRange)
    }

    fun updateFactAddress(newAddress: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryFactAddress = newAddress)
    }

    fun updateCity(newCity: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryCity = newCity)
    }

    fun updateState(newState: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryState = newState)
    }

    fun updateCountry(newCountry: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryCountry = newCountry)
    }

    fun updatePhone(newPhone: String) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryPhoneNumber = newPhone)
    }

    fun updateLoadingUnloadingDates(loadingDate: String, unloadingDate: String) {
        _cargoDescFlow.value =
            _cargoDescFlow.value?.copy(loadingDate = loadingDate, unloadingDate = unloadingDate)
    }

    fun updateStatus(newStatus: DeliveryStatus) {
        _cargoDescFlow.value = _cargoDescFlow.value?.copy(deliveryStatus = newStatus)
    }

    fun saveChanges() {
        launchWithErrorHandling({
            val currentCargoDesc = _cargoDescFlow.value

            if (currentCargoDesc == null || cargoDescId == null) {
                return@launchWithErrorHandling
            }

            val updatedCargo = CargoCreateEdit(
                price = currentCargoDesc.price?.toIntOrNull(),
                range = currentCargoDesc.range?.toIntOrNull(),
                loadingDate = currentCargoDesc.loadingDate,
                unloadingDate = currentCargoDesc.unloadingDate,
                factAddress = currentCargoDesc.deliveryFactAddress,
                city = currentCargoDesc.deliveryCity,
                state = currentCargoDesc.deliveryState,
                country = currentCargoDesc.deliveryCountry,
                phoneNumber = currentCargoDesc.deliveryPhoneNumber,
                storageId = currentCargoDesc.storageId ?: 1
            )

            // Сохранение изменений в грузах
            deliveryRepo.editDelivery(cargoDescId!!, updatedCargo).also {
                if (originalStatus != currentCargoDesc.deliveryStatus) {
                    deliveryRepo.changeDeliveryStatus(
                        cargoDescId!!, currentCargoDesc.deliveryStatus ?: DeliveryStatus.WAITING
                    ).also { response ->
                        triggerEvent(DeliveryEvent.DeliveryStatusChanged(response.message ?: "Груз обновлен"))
                    }
                } else {
                    triggerEvent(DeliveryEvent.DeliveryUpdated(it.message ?: "Груз обновлен"))
                }
            }
        })
    }

}