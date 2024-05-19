package kg.fuankan.tezcargo.ui.cargo.add

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentResultListener
import com.design2.chili2.extensions.setOnSingleClickListener
import com.design2.chili2.view.modals.picker.RangeDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.StorageOption
import kg.fuankan.tezcargo.databinding.ActivityAddCargoBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.finishWithResult
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.extensions.toCalendarOrNow
import kg.fuankan.tezcargo.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddCargoActivity : BaseActivity<AddCargoVM, ActivityAddCargoBinding>(
    AddCargoVM::class.java,
    ActivityAddCargoBinding::inflate
), FragmentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.setFragmentResultListener(RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT, this, this)
        collectFlows()
        setupViews()
        setupFormValidation()
    }

    private fun setupViews() {
        with(vb) {
            bivPrice.doAfterTextChanged {
                vm.createModel.price = it.toString().toIntOrNull()
                vm.updateForm()
            }
            bivRange.doAfterTextChanged {
                vm.createModel.range = it.toString().toIntOrNull()
                vm.updateForm()
            }
            bivFactAddress.doAfterTextChanged {
                vm.createModel.factAddress = it.toString()
                vm.updateForm()
            }
            bivCity.doAfterTextChanged {
                vm.createModel.city = it.toString()
                vm.updateForm()
            }
            bivState.doAfterTextChanged {
                vm.createModel.state = it.toString()
                vm.updateForm()
            }
            bivCountry.doAfterTextChanged {
                vm.createModel.country = it.toString()
                vm.updateForm()
            }
            bivPhone.doAfterTextChanged {
                vm.createModel.phoneNumber = it.toString()
                vm.updateForm()
            }
            uivLoadingUnloadingDate.setOnSingleClickListener {
                showDatePicker()
            }
            uivChooseStorage.setOnSingleClickListener {
                vm.getStoragesOptionList()
            }
            btnAddCargo.setOnClickListener {
                vm.createDelivery()
            }
        }
    }

    private fun showDatePicker() {
        val startDate = vm.createModel.loadingDate?.toCalendarOrNow() ?: Calendar.getInstance()
        val endDate = vm.createModel.unloadingDate?.toCalendarOrNow() ?: startDate.apply{ add(Calendar.MONTH, 1) }

        RangeDatePickerDialog.create(
            "Готово",
            "Дата погрузки",
            "Дата разгрузки",
            currentStartDate = startDate,
            currentEndDate = endDate
        ).show(supportFragmentManager, "datePicker")
    }

    private fun setupFormValidation() {
        collectFlow(vm.isFormValid) { isValid ->
            if (isValid != null) {
                vb.btnAddCargo.isEnabled = isValid
            }
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event) {
            when (it) {
                is DeliveryEvent.DeliveryCreated -> {
                    it.note?.let { note -> showToast(note) }
                    finishWithResult()
                }
                is DeliveryEvent.StoragesOptionList -> showStorageOptions(it.list)
                else -> {}
            }
        }
    }

    private fun showStorageOptions(storageOptions: List<StorageOption>?) {
        if (!storageOptions.isNullOrEmpty()) {
            val storageNames = storageOptions.map { it.name }.toTypedArray()

            AlertDialog.Builder(this)
                .setTitle("Выберите склад")
                .setItems(storageNames) { dialog, which ->
                    val selectedStorage = storageOptions[which]
                    vm.createModel.storageId = selectedStorage.id
                    vb.uivChooseStorage.setText(selectedStorage.name)
                    vm.updateForm()
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

            vm.createModel.loadingDate = startDate
            vm.createModel.unloadingDate = endDate
            vb.uivLoadingUnloadingDate.setText("$startDate/$endDate")
            vm.updateForm()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AddCargoActivity::class.java)
            context.startActivity(intent)
        }
    }
}