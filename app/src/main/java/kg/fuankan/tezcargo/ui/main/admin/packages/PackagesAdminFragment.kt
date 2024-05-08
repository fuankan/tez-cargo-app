package kg.fuankan.tezcargo.ui.main.admin.packages

import com.design2.chili2.extensions.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.FragmentPackagesAdminBinding
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.cargo.add.AddCargoActivity
import kg.fuankan.tezcargo.ui.cargo.desc.CargoDescActivity

@AndroidEntryPoint
class PackagesAdminFragment : BaseNavigatedFragment<PackagesAdminVM, FragmentPackagesAdminBinding>(
    PackagesAdminVM::class.java,
    FragmentPackagesAdminBinding::inflate
) {

    override fun setupViews() {
        super.setupViews()
        vb.btnAddCargo.setOnClickListener {
            AddCargoActivity.start(requireContext())
        }

        vb.icvCargoId1.setOnSingleClickListener {
            CargoDescActivity.start(requireContext())
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                else -> {}
            }
        })
    }
}