package kg.fuankan.tezcargo.ui.change_password

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.repo.AuthRepo
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class ChangePasswordVM @Inject constructor(
    private val repo: AuthRepo,
    private val appPreferences: AppPreferences
): BaseVM() {

    private var vmEmail: String? = null

    fun getUserData() {
        showLoading()
        launchWithErrorHandling({
            appPreferences.userId?.toIntOrNull()?.let { userId ->
                repo.getUserData(userId).also { vmEmail = it.email }
            }
        })
    }

    fun changePassword(password: String) {
        showLoading()
        launchWithErrorHandling({
            repo.changePassword(vmEmail!!, password).also {
                triggerEvent(AuthEvent.PasswordChanged(it.message ?: "Пароль изменен"))
            }
        })
    }
}