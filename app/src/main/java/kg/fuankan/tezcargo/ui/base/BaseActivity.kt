package kg.fuankan.tezcargo.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.extensions.collectFlow

open class BaseActivity<viewModel : BaseVM, viewBinding : ViewBinding>(
        private val vmClass: Class<viewModel>,
        inline val bindingCreator: (LayoutInflater) -> viewBinding
) : AppCompatActivity() {

    protected lateinit var vb: viewBinding
    protected lateinit var vm: viewModel
    private val loader by lazy {
        return@lazy createTransparentLoadingDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = bindingCreator(layoutInflater)
        setContentView(vb.root)
        vm = ViewModelProvider(this)[vmClass]
    }

    private fun createTransparentLoadingDialog(): Dialog {
        return AlertDialog.Builder(this)
            .setView(
                LayoutInflater.from(this@BaseActivity).inflate(
                    R.layout.progress_layout, null
                )
            )
            .setCancelable(false)

                .setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        loader.dismiss()
                        finish()
                    }
                    return@setOnKeyListener true
                }

                .create().apply {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
    }

    protected open fun collectFlows() {
        collectFlow(vm.isLoading) {
            when (it) {
                true -> showLoading()
                else -> hideLoading()
            }
        }
    }

    open fun showLoading() {
        loader.show()
    }

    open fun hideLoading() {
        loader.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        loader.dismiss()
    }
}