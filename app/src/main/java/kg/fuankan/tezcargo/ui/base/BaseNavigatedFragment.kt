package kg.fuankan.tezcargo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

open class BaseNavigatedFragment<vm : BaseVM, viewBinding : ViewBinding>(
        vmClass: Class<vm>,
        bindingCreator: (LayoutInflater) -> viewBinding
) : BaseFragment<vm, viewBinding>(vmClass, bindingCreator) {

    protected fun navigateTo(directions: NavDirections, extras: Navigator.Extras? = null) {
        extras?.let {
            findNavController().navigate(directions, extras)
        } ?: findNavController().navigate(directions, navOptions,)
    }

    protected fun navigateTo(@IdRes destinationId: Int, extras: Bundle? = null) {
        extras?.let {
            findNavController().navigate(destinationId, extras,navOptions)
        } ?: findNavController().navigate(destinationId,null, navOptions)
    }

    /** navigateUp will finish activity if stack is empty, popBackStack wont*/
    protected fun popBackStack() = findNavController().popBackStack()
    protected fun navigateUp() = findNavController().navigateUp()


    private val navOptions: NavOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            .build()
    }
}