package kg.fuankan.tezcargo.ui.main.admin.packages

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.design2.chili2.extensions.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.databinding.FragmentPackagesAdminBinding
import kg.fuankan.tezcargo.domain.model.DeliveryEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.cargo.add.AddCargoActivity
import kg.fuankan.tezcargo.ui.cargo.desc.CargoDescActivity
import kg.fuankan.tezcargo.ui.main.admin.find.FindActivity
import kg.fuankan.tezcargo.ui.main.admin.packages.adapter.CargoListAdapter

@AndroidEntryPoint
class PackagesAdminFragment : BaseNavigatedFragment<PackagesAdminVM, FragmentPackagesAdminBinding>(
    PackagesAdminVM::class.java,
    FragmentPackagesAdminBinding::inflate
) {
    
    private lateinit var adapter: CargoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        vm.filterCargo(vm.cargoFilter)
    }

    private val findActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val filter = result.data?.getSerializableExtra(FindActivity.EXTRA_CARGO_FILTER) as? CargoFilter
            filter?.let {
                vm.filterCargo(it)
            }
        }
    }

    private val cargoDescActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            vm.filterCargo(vm.cargoFilter)
        }
    }

    override fun setupViews() {
        super.setupViews()
        adapter = CargoListAdapter(object : CargoListAdapter.CargoClickListener {
            override fun onCargoClick(cargo: CargoDesc) {
                val intent = Intent(requireContext(), CargoDescActivity::class.java).apply {
                    putExtra(CargoDescActivity.EXTRA_CARGO_DESC, cargo)
                }
                cargoDescActivityResultLauncher.launch(intent)
            }
        })

        with(vb) {
            rvCargo.layoutManager = LinearLayoutManager(requireContext())
            rvCargo.adapter = adapter

            btnAddCargo.setOnSingleClickListener {
                AddCargoActivity.start(requireContext())
            }

            btnFind.setOnSingleClickListener {
                val intent = Intent(requireContext(), FindActivity::class.java).apply {
                    putExtra(FindActivity.EXTRA_CARGO_FILTER, vm.cargoFilter)
                }
                findActivityResultLauncher.launch(intent)
            }
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                is DeliveryEvent.FilteredDeliveryList -> {
                    updateCargoList(it.list)
                }
                else -> {}
            }
        })
    }

    private fun updateCargoList(list: List<CargoDesc>?) {
        if (list.isNullOrEmpty()) {
            vb.rvCargo.visibility = View.GONE
            vb.llNoCargo.visibility = View.VISIBLE
        } else {
            vb.rvCargo.visibility = View.VISIBLE
            vb.llNoCargo.visibility = View.GONE
            adapter.submitList(list)
        }
    }
}
