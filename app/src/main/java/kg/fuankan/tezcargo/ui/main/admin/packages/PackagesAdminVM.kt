package kg.fuankan.tezcargo.ui.main.admin.packages

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.data.repo.DeliveryRepo
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackagesAdminVM @Inject constructor(
    private val deliveryRepo: DeliveryRepo
) : BaseVM() {

    var cargoFilter = CargoFilter(statuses = emptyList())

    fun filterCargo(cargoFilter: CargoFilter) {
        showLoading()
        this.cargoFilter = cargoFilter

        viewModelScope.launch {
            try {
                deliveryRepo.getFilteredDeliveries(cargoFilter).also {
                    triggerEvent(DeliveryEvent.FilteredDeliveryList(it))
                }
            } catch (e: Exception) {
                handleError(e)
                triggerEvent(DeliveryEvent.FilteredDeliveryList(emptyList()))
            } finally {
                hideLoading()
            }
        }
    }
}