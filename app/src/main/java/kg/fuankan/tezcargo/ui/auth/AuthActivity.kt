package kg.fuankan.tezcargo.ui.auth

import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.ActivityAuthBinding
import kg.fuankan.tezcargo.ui.base.BaseNavigatedActivity

@AndroidEntryPoint
class AuthActivity : BaseNavigatedActivity<AuthVM, ActivityAuthBinding>(
    AuthVM::class.java,
    R.id.auth_nav_host,
    ActivityAuthBinding::inflate
) {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }
    }
}