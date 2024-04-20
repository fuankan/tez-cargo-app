package kg.fuankan.tezcargo.ui.auth.login

import android.util.Patterns
import com.design2.chili2.extensions.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.FragmentLoginBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.domain.model.Event
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.ui.auth.AuthVM
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment

@AndroidEntryPoint
class LoginFragment : BaseNavigatedFragment<AuthVM, FragmentLoginBinding>(
    AuthVM::class.java,
    FragmentLoginBinding::inflate
) {

    private val allowedPassSymbolsPattern = "^[a-zA-Z]*[0-9]*\$".toRegex()

    override fun setupViews() {
        super.setupViews()
        with(vb) {

            bivEmail.apply {
                doAfterTextChanged { toggleButton() }
            }

            bivPassword.apply {
                setupAsPasswordField()
                doAfterTextChanged { toggleButton() }
                addRegexFilter(allowedPassSymbolsPattern)
            }

            btnLogin.isEnabled = false

            tvRegister.setOnSingleClickListener {
                navigateTo(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
        }
    }

    private fun toggleButton() {
        vb.btnLogin.isEnabled = true
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