package kg.fuankan.tezcargo.ui.main.admin.menu

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class MenuAdminVM @Inject constructor(
    private val prefs: AppPreferences
) : BaseVM() {

    fun logout() {
        prefs.clear()
        triggerEvent(AuthEvent.Logout)
    }
}