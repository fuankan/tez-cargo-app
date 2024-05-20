package kg.fuankan.tezcargo.ui.driver_list.verification

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.DriverInfo
import kg.fuankan.tezcargo.databinding.FragmentVerificationDriverListBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.gone
import kg.fuankan.tezcargo.extensions.visible
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.driver_list.DriverListVM
import kg.fuankan.tezcargo.ui.driver_list.adapter.DriverListAdapter

@AndroidEntryPoint
class VerificationDriverListFragment : BaseNavigatedFragment<DriverListVM, FragmentVerificationDriverListBinding>(
    DriverListVM::class.java,
    FragmentVerificationDriverListBinding::inflate
), DriverListAdapter.DriverListAdapterListener {

    private lateinit var driverListAdapter: DriverListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getDriverRequests()
        setupRecyclerView()
        vb.ivBack.setOnClickListener {
            navigateUp()
        }
    }

    private fun setupRecyclerView() {
        driverListAdapter = DriverListAdapter(this, true)
        with(vb.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = driverListAdapter
        }
    }

    private fun initAdapter(list: List<DriverInfo>?) {
        if (list.isNullOrEmpty()) {
            vb.recyclerView.gone()
            vb.llNoDrivers.visible()
        } else {
            vb.recyclerView.visible()
            vb.llNoDrivers.gone()
            driverListAdapter.submitList(list)
        }
    }

    private fun showVerificationDialog(driverId: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage("Верифицировать данного водителя?")
            .setPositiveButton("Да") { _, _ ->
                vm.verifyDriver(driverId, true)
            }
            .setNegativeButton("Нет", null)
            .show()
    }

    override fun onDriverClick(driver: DriverInfo) {
        driver.driverId?.let { showVerificationDialog(it) }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                is DeliveryEvent.DriverListFetched -> initAdapter(it.list)
                is DeliveryEvent.DriverVerified -> vm.getDriverRequests()
                else -> {}
            }
        })
    }
}
