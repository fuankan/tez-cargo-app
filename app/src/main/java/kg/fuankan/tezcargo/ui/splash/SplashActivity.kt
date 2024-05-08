package kg.fuankan.tezcargo.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.ActivitySplashBinding
import kg.fuankan.tezcargo.ui.auth.AuthActivity
import kg.fuankan.tezcargo.ui.base.BaseActivity
import kg.fuankan.tezcargo.ui.main.admin.MainAdminActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashVM, ActivitySplashBinding>(
    SplashVM::class.java,
    { ActivitySplashBinding.inflate(it) }
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            vm.isUserNotAuthenticated() -> {
                Handler(mainLooper).postDelayed({
                    AuthActivity.start(this)
                    finish()
                }, 2000)
            }
        }
    }
}