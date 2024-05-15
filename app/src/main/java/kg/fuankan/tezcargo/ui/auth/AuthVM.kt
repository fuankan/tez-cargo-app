package kg.fuankan.tezcargo.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.models.LoginResponse
import kg.fuankan.tezcargo.data.repo.AuthRepo
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor (
    private val authRepo: AuthRepo,
    private val preferences: AppPreferences
) : BaseVM() {

    fun login(username: String, password: String) {
        showLoading()
        launchWithErrorHandling({
            authRepo.login(username, password).also {
                saveLoginResponse(it)
                triggerEvent(AuthEvent.LoginSuccess)
            }
        })
    }

    private fun saveLoginResponse(resp: LoginResponse) {
        with(preferences) {
            token = resp.token
            role = resp.role.name
            userId = resp.userId.toString()
        }
    }
}