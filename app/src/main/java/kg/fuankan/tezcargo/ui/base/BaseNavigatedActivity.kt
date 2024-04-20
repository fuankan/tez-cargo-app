package kg.fuankan.tezcargo.ui.base

import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding

open class BaseNavigatedActivity<viewModel : BaseVM, viewBinding : ViewBinding>(
        vmClass: Class<viewModel>,
        @IdRes private val navControllerResId: Int,
        bindingCreator: (LayoutInflater) -> viewBinding
) : BaseActivity<viewModel, viewBinding>(vmClass, bindingCreator) {

    protected val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(navControllerResId) as NavHostFragment
        navHostFragment.navController
    }

    fun navigateTo(navDirections: NavDirections) = navController.navigate(navDirections)
}