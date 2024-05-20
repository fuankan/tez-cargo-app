package kg.fuankan.tezcargo.ui.driver_list.main

import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.FragmentDriverListBinding
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.driver_list.DriverListVM

@AndroidEntryPoint
class DriverListFragment : BaseNavigatedFragment<DriverListVM, FragmentDriverListBinding>(
    DriverListVM::class.java,
    FragmentDriverListBinding::inflate
) {
    override fun setupViews() {
        super.setupViews()
        with(vb) {
            bcvDriversOnVerification.setOnClickListener {
                navigateTo(DriverListFragmentDirections.actionDriverListFragmentToVerificationDriverListFragment())
            }

            bcvDriversAccepted.setOnClickListener {
                navigateTo(DriverListFragmentDirections.actionDriverListFragmentToAcceptedDriverListFragment())
            }

            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}
