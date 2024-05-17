package kg.fuankan.tezcargo.ui.main.admin.menu

import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.FragmentMenuAdminBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.ui.auth.AuthActivity
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment

@AndroidEntryPoint
class MenuAdminFragment : BaseNavigatedFragment<MenuAdminVM, FragmentMenuAdminBinding>(
    MenuAdminVM::class.java,
    FragmentMenuAdminBinding::inflate
) {

    override fun setupViews() {
        vb.apply {
            icvCargoId4.setOnClickListener {
                vm.logout()
            }
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                is AuthEvent.Logout -> {
                    AuthActivity.start(requireContext())
                    requireActivity().finish()
                }
                else -> {}
            }
        })
    }
}