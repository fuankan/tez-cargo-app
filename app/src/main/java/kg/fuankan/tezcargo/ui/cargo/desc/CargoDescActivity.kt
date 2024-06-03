package kg.fuankan.tezcargo.ui.cargo.desc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentResultListener
import com.design2.chili2.extensions.setOnSingleClickListener
import com.design2.chili2.view.modals.picker.RangeDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.data.models.*
import kg.fuankan.tezcargo.databinding.ActivityCargoDescBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.finishWithResult
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.extensions.toCalendarOrNow
import kg.fuankan.tezcargo.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CargoDescActivity : BaseActivity<CargoDescVM, ActivityCargoDescBinding>(
    CargoDescVM::class.java,
    ActivityCargoDescBinding::inflate
), FragmentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getSerializableExtra(EXTRA_CARGO_DESC)?.let { cargoDesc ->
            vm.cargoDescId = (cargoDesc as CargoDesc).deliveryId
        }
        collectFlows()
        supportFragmentManager.setFragmentResultListener(RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT, this, this)
        vm.getDeliveryById(vm.cargoDescId ?: 1)
    }

    private fun setupViews(cargoDesc: CargoDesc) {
        with(vb) {
            ivBack.setOnSingleClickListener { onBackPressedDispatcher.onBackPressed() }
            tvCargoDesc.text = getString(R.string.cargo_with_id, cargoDesc.deliveryId)
            icvStatus.setTitle(cargoDesc.deliveryStatus?.status ?: "")
            icvPrice.setTitle("${cargoDesc.price ?: ""} с")
            icvRange.setTitle("${cargoDesc.range ?: ""} км")
            icvLoadingUnloadingDate.setTitle("${cargoDesc.loadingDate}/${cargoDesc.unloadingDate}")
            icvFactAddress.setTitle(cargoDesc.deliveryFactAddress ?: "")
            icvCity.setTitle(cargoDesc.deliveryCity ?: "")
            icvState.setTitle(cargoDesc.deliveryState ?: "")
            icvCountry.setTitle(cargoDesc.deliveryCountry ?: "")
            icvPhone.setTitle(cargoDesc.deliveryPhoneNumber ?: "")
            icvStorage.setTitle(cargoDesc.storageName ?: "")
            tvStorageAddress.text = "${cargoDesc.storageFactAddress}\n${cargoDesc.storageGeoLocation}\n${cargoDesc.storagePhoneNumber}"
            if(cargoDesc.driverPhoneNumber?.contains("Нет водителя") == true) {
                tvDriverData.text = "${cargoDesc.driverName}"
            } else {
                tvDriverData.text = "${cargoDesc.driverName}\n${cargoDesc.driverPhoneNumber}"
            }

            icvStatus.setOnSingleClickListener { showStatusPicker() }
            icvPrice.setOnSingleClickListener { showInputDialog("Price") { newValue -> vm.updatePrice(newValue) } }
            icvRange.setOnSingleClickListener { showInputDialog("Range") { newValue -> vm.updateRange(newValue) } }
            icvLoadingUnloadingDate.setOnSingleClickListener { showDatePicker() }
            icvFactAddress.setOnSingleClickListener { showInputDialog("Fact Address") { newValue -> vm.updateFactAddress(newValue) } }
            icvCity.setOnSingleClickListener { showInputDialog("City") { newValue -> vm.updateCity(newValue) } }
            icvState.setOnSingleClickListener { showInputDialog("State") { newValue -> vm.updateState(newValue) } }
            icvCountry.setOnSingleClickListener { showInputDialog("Country") { newValue -> vm.updateCountry(newValue) } }
            icvPhone.setOnSingleClickListener { showInputDialog("Phone") { newValue -> vm.updatePhone(newValue) } }
            icvStorage.setOnSingleClickListener { vm.getStoragesOptionList() }
            btnSave.setOnSingleClickListener { vm.saveChanges() }
        }
    }

    private fun showInputDialog(title: String, onValueChanged: (String) -> Unit) {
        val currentValue = when (title) {
            "Price" -> vm.cargoDesc?.price
            "Range" -> vm.cargoDesc?.range
            "Fact Address" -> vm.cargoDesc?.deliveryFactAddress
            "City" -> vm.cargoDesc?.deliveryCity
            "State" -> vm.cargoDesc?.deliveryState
            "Country" -> vm.cargoDesc?.deliveryCountry
            "Phone" -> vm.cargoDesc?.deliveryPhoneNumber
            else -> ""
        }

        val input = EditText(this).apply {
            setText(currentValue)
            inputType = if (title == "Price" || title == "Range") {
                InputType.TYPE_CLASS_NUMBER
            } else {
                InputType.TYPE_CLASS_TEXT
            }
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val newValue = input.text.toString()
                onValueChanged(newValue)
                updateTitle(title, newValue)
                enableSaveButton()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTitle(title: String, newValue: String) {
        when (title) {
            "Price" -> vb.icvPrice.setTitle("$newValue с")
            "Range" -> vb.icvRange.setTitle("$newValue км")
            "Fact Address" -> vb.icvFactAddress.setTitle(newValue)
            "City" -> vb.icvCity.setTitle(newValue)
            "State" -> vb.icvState.setTitle(newValue)
            "Country" -> vb.icvCountry.setTitle(newValue)
            "Phone" -> vb.icvPhone.setTitle(newValue)
        }
    }

    private fun showDatePicker() {
        val startDate = vm.cargoDesc?.loadingDate?.toCalendarOrNow() ?: Calendar.getInstance()
        val endDate = vm.cargoDesc?.unloadingDate?.toCalendarOrNow() ?: Calendar.getInstance()

        RangeDatePickerDialog.create(
            "Готово",
            "Дата погрузки",
            "Дата разгрузки",
            currentStartDate = startDate,
            currentEndDate = endDate
        ).show(supportFragmentManager, "datePicker")
    }

    private fun showStatusPicker() {
        val statuses = DeliveryStatus.entries.toTypedArray()
        val statusLabels = statuses.map { it.status }.toTypedArray()
        val selectedStatus = statuses.indexOf(vm.cargoDesc?.deliveryStatus)

        AlertDialog.Builder(this)
            .setTitle("Выберите статус")
            .setSingleChoiceItems(statusLabels, selectedStatus) { dialog, which ->
                val selected = statuses[which]
                vm.updateStatus(selected)
                vb.icvStatus.setTitle(selected.status)
                enableSaveButton()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun enableSaveButton() {
        vb.btnSave.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event) {
            when (it) {
                is DeliveryEvent.DeliveryById -> setupViews(it.cargoDesc)
                is DeliveryEvent.StorageInfoFetched -> updateStorageInfo(it.storageInfo)
                is DeliveryEvent.StoragesOptionList -> showStorageOptions(it.list)
                is DeliveryEvent.DeliveryUpdated -> {
                    it.note?.let { note -> showToast(note) }
                    finishWithResult()
                }
                is DeliveryEvent.DeliveryStatusChanged -> {
                    it.note?.let { note -> showToast(note) }
                    finishWithResult()
                }
                else -> {}
            }
        }
    }

    private fun updateStorageInfo(storageInfo: StorageInfo?) {
        storageInfo?.let {
            vb.icvStorage.setTitle(it.storageName)
            vb.tvStorageAddress.text = "${it.storageFactAddress}\n${it.storageGeoLocation}\n${it.storagePhoneNumber}"
            enableSaveButton()
        }
    }

    private fun showStorageOptions(storageOptions: List<StorageOption>?) {
        if (!storageOptions.isNullOrEmpty()) {
            val storageNames = storageOptions.map { it.name }.toTypedArray()

            AlertDialog.Builder(this)
                .setTitle("Выберите склад")
                .setItems(storageNames) { dialog, which ->
                    val selectedStorage = storageOptions[which]
                    vm.getStorageInfoById(selectedStorage.id)
                    vm.updateStorageId(selectedStorage.id)
                    vb.icvStorage.setTitle(selectedStorage.name)
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена", null)
                .show()
        } else {
            showToast("Список складов пуст")
        }
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        if (requestKey == RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT) {
            val calendarStart = result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_START_DATE) as Calendar
            val calendarEnd = result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_END_DATE) as Calendar

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = dateFormat.format(calendarStart.time)
            val endDate = dateFormat.format(calendarEnd.time)

            vm.updateLoadingUnloadingDates(startDate, endDate)
            vb.icvLoadingUnloadingDate.setTitle("$startDate/$endDate")
            enableSaveButton()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.clearViewModel()
    }

    companion object {
        const val EXTRA_CARGO_DESC = "extra_cargo_desc"

        fun start(context: Context, cargoDesc: CargoDesc) {
            val intent = Intent(context, CargoDescActivity::class.java).apply {
                putExtra(EXTRA_CARGO_DESC, cargoDesc)
            }
            context.startActivity(intent)
        }
    }
}