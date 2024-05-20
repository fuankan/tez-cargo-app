package kg.fuankan.tezcargo.ui.change_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.ActivityChangePasswordBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.ui.base.BaseActivity

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ChangePasswordVM, ActivityChangePasswordBinding>(
    ChangePasswordVM::class.java,
    ActivityChangePasswordBinding::inflate
) {

    private val allowedPassSymbolsPattern = "^[a-zA-Z]*[0-9]*\$".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectFlows()
        vm.getUserData()
        with(vb) {
            btnChange.isEnabled = false

            bivInput.apply {
                setHint(getString(R.string.enter_password))
                setupAsPasswordField()
                doAfterTextChanged {
                    clearFieldError()
                    toggleButton()
                }
                addRegexFilter(allowedPassSymbolsPattern)
                setMaxLength(12)
            }

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnChange.setOnClickListener {
                if (bivInput.getInputText().isNotEmpty()) {
                    vm.changePassword(bivInput.getInputText())
                }
            }
        }
    }

    private fun toggleButton() {
        with(vb) {
            btnChange.isEnabled = bivInput.getInputText().isNotEmpty()
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event) {
            when (it) {
                is AuthEvent.PasswordChanged -> {
                    showToast(it.note)
                    finish()
                }
                else -> {}
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
    }
}