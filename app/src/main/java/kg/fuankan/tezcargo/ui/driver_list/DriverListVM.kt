package kg.fuankan.tezcargo.ui.driver_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.repo.DeliveryRepo
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class DriverListVM @Inject constructor(
    private val repository: DeliveryRepo
): BaseVM() {

    fun verifyDriver(driverId: Int, isVerified: Boolean) {
        showLoading()
        launchWithErrorHandling({
            repository.verifyDriver(driverId, isVerified).also {
                triggerEvent(DeliveryEvent.DriverVerified(it.message ?: "Водитель верифицирован"))
            }
        })
    }

    fun getDriverRequests() {
        showLoading()
        launchWithErrorHandling({
            repository.getDriverRequests().also {
                triggerEvent(DeliveryEvent.DriverListFetched(it))
            }
        })
    }

    fun getAcceptedDrivers() {
        showLoading()
        launchWithErrorHandling({
            repository.getAcceptedDrivers().also {
                triggerEvent(DeliveryEvent.DriverListFetched(it))
            }
        })
    }
}