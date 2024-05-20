package kg.fuankan.tezcargo.ui.driver_list.accepted

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.DriverInfo
import kg.fuankan.tezcargo.databinding.FragmentAcceptedDriverListBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.gone
import kg.fuankan.tezcargo.extensions.visible
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.driver_list.DriverListVM
import kg.fuankan.tezcargo.ui.driver_list.adapter.DriverListAdapter

@AndroidEntryPoint
class AcceptedDriverListFragment : BaseNavigatedFragment<DriverListVM, FragmentAcceptedDriverListBinding>(
    DriverListVM::class.java,
    FragmentAcceptedDriverListBinding::inflate
), DriverListAdapter.DriverListAdapterListener {

    private lateinit var driverListAdapter: DriverListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getAcceptedDrivers()
        setupRecyclerView()
        vb.ivBack.setOnClickListener {
            navigateUp()
        }
    }

    private fun setupRecyclerView() {
        driverListAdapter = DriverListAdapter(this, false)
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

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when(it) {
                is DeliveryEvent.DriverListFetched -> {
                    initAdapter(it.list)
                }
                else -> {}
            }
        })
    }

    override fun onDriverClick(driver: DriverInfo) {}
}