package kg.fuankan.tezcargo.ui.main.admin.menu

import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.FragmentMenuAdminBinding
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment

@AndroidEntryPoint
class MenuAdminFragment : BaseNavigatedFragment<MenuAdminVM, FragmentMenuAdminBinding>(
    MenuAdminVM::class.java,
    FragmentMenuAdminBinding::inflate
) {

}