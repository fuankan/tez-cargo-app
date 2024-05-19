package kg.fuankan.tezcargo.ui.statistics

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.repo.DeliveryRepo
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class StatisticsVM @Inject constructor(
    private val deliveryRepo: DeliveryRepo
): BaseVM() {

    fun getAccounting(type: String) {
        showLoading()
        launchWithErrorHandling( {
            deliveryRepo.getAccounting(type).also {
                triggerEvent(DeliveryEvent.AccountingFetched(it.message))
            }
        })
    }
}