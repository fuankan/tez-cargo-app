package kg.fuankan.tezcargo.ui.auth.reset

import android.util.Patterns
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.FragmentResetBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.domain.model.Event
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.ui.auth.AuthVM
import kg.fuankan.tezcargo.ui.base.BaseNavigatedFragment

@AndroidEntryPoint
class ResetFragment : BaseNavigatedFragment<AuthVM, FragmentResetBinding>(
    AuthVM::class.java,
    FragmentResetBinding::inflate
) {

    private val allowedPassSymbolsPattern = "^[a-zA-Z]*[0-9]*\$".toRegex()

    override fun setupViews() {
        super.setupViews()
        vb.btnFurther.isEnabled = false
        setupEnterEmail()
    }

    private fun setupEnterEmail() = with(vb) {
        bivInput.apply {
            setHint(getString(R.string.enter_email))
            doAfterTextChanged {
                clearFieldError()
                toggleButton()
            }
        }

        btnFurther.setOnClickListener {
            if (isEmailValid()) {
                vm.resetPassword(bivInput.getInputText())
            }
        }
    }

    private fun setupEnterOtp() = with(vb) {
        bivInput.apply {
            setHint(getString(R.string.otp))
            doAfterTextChanged {
                clearFieldError()
                toggleButton()
            }
            setMaxLength(6)
        }

        btnFurther.setOnClickListener {
            if (bivInput.getInputText().isNotEmpty()) {
                vm.checkOtp(bivInput.getInputText())
            }
        }
    }

    private fun setupEnterPassword() = with(vb) {
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

        btnFurther.setOnClickListener {
            if (bivInput.getInputText().isNotEmpty()) {
                vm.changePassword(bivInput.getInputText())
            }
        }
    }

    override fun collectFlows() {
        super.collectFlows()
        collectFlow(vm.event, {
            when (it) {
                is Event.Notification -> {
                    requireContext().showToast(it.text)
                }
                is Event.NotificationRes -> {
                    requireContext().showToast(it.stringRes)
                }
                is AuthEvent.OtpSent -> {
                    requireContext().showToast(it.note)
                    vb.bivInput.setText("")
                    setupEnterOtp()
                }
                is AuthEvent.OtpChecked -> {
                    requireContext().showToast(it.note)
                    vb.bivInput.setText("")
                    setupEnterPassword()
                }
                is AuthEvent.PasswordChanged -> {
                    requireContext().showToast(it.note)
                    popBackStack()
                }
                else -> {}
            }
        })
    }

    private fun toggleButton() {
        with(vb) {
            btnFurther.isEnabled = bivInput.getInputText().isNotEmpty()
        }
    }

    private fun isEmailValid(): Boolean {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(vb.bivInput.getInputText()).matches()
        when {
            vb.bivInput.getInputText().isEmpty() -> vb.bivInput.clearFieldError()
            isValid -> vb.bivInput.clearFieldError()
            else -> {
                vb.bivInput.apply {
                    setText("")
                    setupFieldAsError(R.string.error_invalid_email)
                }
            }
        }
        return isValid
    }
}
