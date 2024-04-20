package kg.fuankan.tezcargo.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.ui.base.BaseVM
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val prefs: AppPreferences
) : BaseVM() {

}