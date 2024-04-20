package kg.fuankan.tezcargo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.annotations.UsesSharedViewModel
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.gone
import kg.fuankan.tezcargo.extensions.hideKeyboard
import kotlinx.coroutines.*

open class BaseFragment<vm : BaseVM, viewBinding : ViewBinding>(
        private val vmClass: Class<vm>,
        open inline val bindingCreator: (LayoutInflater) -> viewBinding
): Fragment() {

    private lateinit var fragmentViewScope: CoroutineScope
    protected lateinit var vb: viewBinding
    protected lateinit var vm: vm
    protected var parentActivity: BaseActivity<*, *>? = null

    protected open var toolbar: Toolbar? = null
    @StringRes
    protected open val toolbarTitle = R.string.title_main
    @DrawableRes
    protected open val navigationIcon = R.drawable.ic_notifications_black_24dp
    @MenuRes
    protected open var menu: Int? = null
    protected open var onNavigationClickListener: View.OnClickListener? = View.OnClickListener {
        activity?.run {
            hideKeyboard()
            onBackPressed()
        }
    }
    protected open var onMenuItemClickListener: Toolbar.OnMenuItemClickListener? = null


    private val vmStoreOwner: ViewModelStoreOwner
        get() = when (usesSharedViewModel) {
            true -> requireActivity()
            false -> this
        }

    private val usesSharedViewModel
        get() = this::class.java.annotations.any { it is UsesSharedViewModel }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            parentActivity =  context as BaseActivity<*, *>

        }catch (e: Throwable){
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(vmStoreOwner)[vmClass]
        collectFlows()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = bindingCreator(inflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewScope = CoroutineScope(Dispatchers.Main)
        setupToolbar()
        setupViews()
    }

    private fun setupToolbar() {
        toolbar?.let { toolbar->
            toolbar.setTitle(toolbarTitle)
            toolbar.setNavigationIcon(navigationIcon)
            onNavigationClickListener?.let {
                toolbar.setNavigationOnClickListener(it)
            }
            menu?.let {
                toolbar.inflateMenu(it)
            }
            onMenuItemClickListener?.let {
                toolbar.setOnMenuItemClickListener(it)
            }
        }
    }

    open fun setupViews() {}

    protected fun hideToolbar(){
        toolbar?.gone()
    }

    protected open fun collectFlows() {
        collectFlow(vm.isLoading, {
            when (it) {
                true -> showLoading()
                else -> hideLoading()
            }
        })
    }

    open fun showLoading(){
        parentActivity?.showLoading()
    }

    open fun hideLoading(){
        parentActivity?.hideLoading()
    }

    fun runDelayed(call: () -> Unit, delayInMillis: Long){
        fragmentViewScope.launch {
            delay(delayInMillis)
            call()
        }
    }

    override fun onDestroyView() {
        fragmentViewScope.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }
}