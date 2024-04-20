package kg.fuankan.tezcargo.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.ActivityAuthBinding
import kg.fuankan.tezcargo.domain.model.AuthEvent
import kg.fuankan.tezcargo.extensions.collectFlow
import kg.fuankan.tezcargo.extensions.showToast
import kg.fuankan.tezcargo.ui.base.BaseNavigatedActivity
import kg.fuankan.tezcargo.ui.main.MainActivity

@AndroidEntryPoint
class AuthActivity : BaseNavigatedActivity<AuthVM, ActivityAuthBinding>(
    AuthVM::class.java,
    R.id.auth_nav_host,
    ActivityAuthBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectFlow(vm.event) {
            when (it) {
                is AuthEvent.LoginSuccess -> finishAuth()
                else -> {
                    showToast(R.string.error_unknown)
                }
            }
        }
    }

    private fun finishAuth() {
        MainActivity.start(this)
        finish()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }
    }
}