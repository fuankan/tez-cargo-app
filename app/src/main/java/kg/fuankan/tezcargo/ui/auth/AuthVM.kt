package kg.fuankan.tezcargo.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor (
    private val preferences: AppPreferences
) : BaseVM() {

}