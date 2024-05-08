package kg.fuankan.tezcargo.ui.auth.reset

import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.FragmentResetBinding
import kg.fuankan.tezcargo.ui.auth.AuthVM
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment

@AndroidEntryPoint
class ResetFragment : BaseNavigatedFragment<AuthVM, FragmentResetBinding>(
    AuthVM::class.java,
    FragmentResetBinding::inflate
) {

    override fun setupViews() {
        super.setupViews()

    }
}
