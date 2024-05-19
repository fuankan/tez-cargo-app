package kg.fuankan.tezcargo.ui.cargo.add

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.models.CargoCreateEdit
import kg.fuankan.tezcargo.data.repo.DeliveryRepo
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddCargoVM @Inject constructor(
    private val repository: DeliveryRepo
): BaseVM() {

    val createModel = CargoCreateEdit()
    val isFormValid = MutableStateFlow(false)

    fun createDelivery() {
        showLoading()
        launchWithErrorHandling({
            repository.createDelivery(createModel).also {
                triggerEvent(
                    DeliveryEvent.DeliveryCreated(it.message ?: "Грузоперевозка успешно создана")
                )
            }
        })
    }

    fun getStoragesOptionList() {
        showLoading()
        launchWithErrorHandling({
            repository.getStorages().also {
                triggerEvent(DeliveryEvent.StoragesOptionList(it))
            }
        })
    }

    fun updateForm() {
        isFormValid.value = createModel.price != null &&
                createModel.range != null &&
                !createModel.loadingDate.isNullOrEmpty() &&
                !createModel.unloadingDate.isNullOrEmpty() &&
                !createModel.factAddress.isNullOrEmpty() &&
                !createModel.city.isNullOrEmpty() &&
                !createModel.state.isNullOrEmpty() &&
                !createModel.country.isNullOrEmpty() &&
                !createModel.phoneNumber.isNullOrEmpty() &&
                createModel.storageId != null
    }
}
