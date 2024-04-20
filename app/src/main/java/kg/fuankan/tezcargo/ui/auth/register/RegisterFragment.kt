package kg.fuankan.tezcargo.ui.auth.register

import android.util.Patterns
import com.design2.chili2.extensions.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.FragmentRegisterBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.domain.model.Event
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.ui.auth.AuthVM
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment
import kg.fuankan.tezcargo.ui.main.MainActivity

@AndroidEntryPoint
class RegisterFragment : BaseNavigatedFragment<AuthVM, FragmentRegisterBinding>(
    AuthVM::class.java,
    FragmentRegisterBinding::inflate
) {

    override fun setupViews() {
        super.setupViews()
        val allowedPassSymbolsPattern = "^[a-zA-Z]*[0-9]*\$".toRegex()

        with(vb) {
            bivEmail.apply {
                doAfterTextChanged { toggleButton() }
            }

            bivPassword.apply {
                setupAsPasswordField()
                doAfterTextChanged { toggleButton() }
                addRegexFilter(allowedPassSymbolsPattern)
            }

            btnRegister.isEnabled = false

            btnRegister.setOnSingleClickListener {
                MainActivity.start(requireContext())
            }
        }
    }

    private fun toggleButton() {
        vb.btnRegister.isEnabled = true
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                is AuthEvent.LoginSuccess -> {

                }
                is Event.Notification -> {
                    requireContext().showToast(it.text)
                }
                is Event.NotificationRes -> {
                    requireContext().showToast(it.stringRes)
                }
                else -> {}
            }
        })
    }

    private fun isEmailValid(): Boolean {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(vb.bivEmail.getInputText()).matches()
        when {
            vb.bivEmail.getInputText().isEmpty() -> vb.bivEmail.clearFieldError()
            isValid -> vb.bivEmail.clearFieldError()
            else -> {
                vb.bivEmail.apply {
                    setText("")
                    setupFieldAsError(R.string.error_invalid_email)
                }
            }
        }
        return isValid
    }
}