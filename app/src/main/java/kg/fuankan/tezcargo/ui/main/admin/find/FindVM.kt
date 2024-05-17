package kg.fuankan.tezcargo.ui.main.admin.find

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.data.models.DeliveryStatus
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class FindVM @Inject constructor(): BaseVM() {

    var cargoFilter: CargoFilter? = null

    fun prepareCargoFilter(
        driverName: String,
        companyName: String,
        filialName: String,
        loadingUnloadingDate: String,
        rangeFrom: Int?,
        rangeTill: Int?,
        priceFrom: Int?,
        priceTill: Int?,
        statuses: String
    ) : CargoFilter {
        var loadingDate = ""
        var unloadingDate = ""
        loadingUnloadingDate.split("/").let {
            if (it.size == 2) {
                loadingDate = it[0]
                unloadingDate = it[1]
            }
        }
        cargoFilter = CargoFilter(
            driverName = driverName,
            companyName = companyName,
            filialName = filialName,
            loadingDate = loadingDate,
            unloadingDate = unloadingDate,
            rangeFrom = rangeFrom,
            rangeTill = rangeTill,
            priceFrom = priceFrom,
            priceTill = priceTill,
            statuses = statuses.split(", ").mapNotNull { statusString ->
                DeliveryStatus.entries.find { it.status.trim() == statusString.trim() }
            }
        )
        return cargoFilter!!
    }

    fun updateLoadingUnloadingDate(loadingDate: String, unloadingDate: String) {
        cargoFilter?.loadingDate = loadingDate
        cargoFilter?.unloadingDate = unloadingDate
    }
}
