package kg.fuankan.tezcargo.ui.main.admin.find

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentResultListener
import com.design2.chili2.extensions.setOnSingleClickListener
import com.design2.chili2.view.modals.picker.RangeDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.data.models.DeliveryStatus
import kg.fuankan.tezcargo.databinding.ActivityFindBinding
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.extensions.toCalendarOrNow
import kg.fuankan.tezcargo.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FindActivity : BaseActivity<FindVM, ActivityFindBinding>(
    FindVM::class.java,
    ActivityFindBinding::inflate
), FragmentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.setFragmentResultListener(RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT, this, this)
        vm.cargoFilter = intent.getSerializableExtra(EXTRA_CARGO_FILTER) as? CargoFilter ?: CargoFilter()
        setupViews()
    }

    private fun setupViews() {
        with(vb) {
            ivBack.setOnSingleClickListener { onBackPressedDispatcher.onBackPressed() }

            btnFind.isEnabled = false

            bivDriverName.apply {
                setText(vm.cargoFilter?.driverName)
                doAfterTextChanged { toggleButton() }
            }

            bivCompanyName.apply {
                setText(vm.cargoFilter?.companyName)
                doAfterTextChanged { toggleButton() }
            }

            bivFilialName.apply {
                setText(vm.cargoFilter?.filialName)
                doAfterTextChanged { toggleButton() }
            }

            uivLoadingUnloadingDate.apply {
                setText(
                    when {
                        vm.cargoFilter?.loadingDate.isNullOrEmpty() || vm.cargoFilter?.unloadingDate.isNullOrEmpty() -> ""
                        else -> "${vm.cargoFilter?.loadingDate}/${vm.cargoFilter?.unloadingDate}"
                    }
                )
                setOnSingleClickListener {
                    RangeDatePickerDialog.create(
                        "Готово",
                        "Дата погрузки",
                        "Дата разгрузки",
                        currentStartDate = vm.cargoFilter?.loadingDate?.toCalendarOrNow() ?: Calendar.getInstance(),
                        currentEndDate = vm.cargoFilter?.unloadingDate?.toCalendarOrNow() ?: Calendar.getInstance()
                    ).show(supportFragmentManager, "")
                }
            }

            bivRangeFrom.apply {
                setText(vm.cargoFilter?.rangeFrom?.toString() ?: "")
                doAfterTextChanged { toggleButton() }
            }

            bivRangeTill.apply {
                setText(vm.cargoFilter?.rangeTill?.toString() ?: "")
                doAfterTextChanged { toggleButton() }
            }

            bivPriceFrom.apply {
                setText(vm.cargoFilter?.priceFrom?.toString() ?: "")
                doAfterTextChanged { toggleButton() }
            }

            bivPriceTill.apply {
                setText(vm.cargoFilter?.priceTill?.toString() ?: "")
                doAfterTextChanged { toggleButton() }
            }

            uivChooseStatus.apply {
                setText(vm.cargoFilter?.statuses?.joinToString { it.status })
                setOnSingleClickListener {
                    showStatusPicker()
                }
            }

            btnFind.setOnSingleClickListener {
                val filter = vm.prepareCargoFilter(
                    driverName = bivDriverName.getInputText(),
                    companyName = bivCompanyName.getInputText(),
                    filialName = bivFilialName.getInputText(),
                    loadingUnloadingDate = uivLoadingUnloadingDate.getInputText(),
                    rangeFrom = bivRangeFrom.getInputText().toIntOrNull(),
                    rangeTill = bivRangeTill.getInputText().toIntOrNull(),
                    priceFrom = bivPriceFrom.getInputText().toIntOrNull(),
                    priceTill = bivPriceTill.getInputText().toIntOrNull(),
                    statuses = uivChooseStatus.getInputText()
                )

                showToast("Фильтр применен")

                val resultIntent = Intent().apply {
                    putExtra(EXTRA_CARGO_FILTER, filter)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            btnReset.setOnSingleClickListener {
                showToast("Фильтр сброшен")
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_CARGO_FILTER, CargoFilter(statuses = emptyList()))
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun showStatusPicker() {
        val statuses = DeliveryStatus.entries.toTypedArray()
        val statusLabels = statuses.map { it.status }.toTypedArray()
        val selectedStatuses = vm.cargoFilter?.statuses?.toMutableList() ?: mutableListOf()

        val selectedItems = BooleanArray(statuses.size) { index ->
            selectedStatuses.contains(statuses[index])
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите статусы")
        builder.setMultiChoiceItems(statusLabels, selectedItems) { _, which, isChecked ->
            if (isChecked) {
                selectedStatuses.add(statuses[which])
            } else {
                selectedStatuses.remove(statuses[which])
            }
        }

        builder.setPositiveButton("OK") { _, _ ->
            val selectedStatusString = selectedStatuses.joinToString { it.status }
            vb.uivChooseStatus.setText(selectedStatusString)
            toggleButton()
            vm.cargoFilter?.statuses = selectedStatuses
        }

        builder.setNegativeButton("Отмена", null)

        builder.create().show()
    }

    private fun toggleButton() {
        with(vb) {
            btnFind.isEnabled =
                bivDriverName.getInputText().isNotEmpty() || bivCompanyName.getInputText().isNotEmpty()
                        || bivFilialName.getInputText().isNotEmpty() || uivLoadingUnloadingDate.getInputText().isNotEmpty()
                        || bivRangeFrom.getInputText().isNotEmpty() || bivRangeTill.getInputText().isNotEmpty() || bivPriceFrom.getInputText().isNotEmpty()
                        || bivPriceTill.getInputText().isNotEmpty() || uivChooseStatus.getInputText().isNotEmpty()
        }
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        if (requestKey == RangeDatePickerDialog.RANGE_PICKER_DIALOG_RESULT) {
            val calendarStart =
                result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_START_DATE) as Calendar
            val calendarEnd =
                result.getSerializable(RangeDatePickerDialog.ARG_SELECTED_END_DATE) as Calendar

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            vb.uivLoadingUnloadingDate.setText("${dateFormat.format(calendarStart.time)}/${dateFormat.format(calendarEnd.time)}").also {
                toggleButton()
            }

            vm.updateLoadingUnloadingDate(
                dateFormat.format(calendarStart.time),
                dateFormat.format(calendarEnd.time)
            )
        }
    }

    companion object {
        const val EXTRA_CARGO_FILTER = "extra_cargo_filter"
    }
}