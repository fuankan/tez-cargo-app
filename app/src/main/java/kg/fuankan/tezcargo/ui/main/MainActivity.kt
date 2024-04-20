package kg.fuankan.tezcargo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.design2.chili2.view.navigation_components.ChiliToolbar
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.ActivityMainBinding
import kg.fuankan.tezcargo.ui.base.BaseNavigatedActivity

@AndroidEntryPoint
class MainActivity : BaseNavigatedActivity<MainVM, ActivityMainBinding>(
    MainVM::class.java,
    R.id.nav_host_fragment_activity_main,
    ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = vb.navView

        vb.toolbar.run{
            initToolbar(
                ChiliToolbar.Configuration(
                    this@MainActivity,
                    onNavigateUpClick = { onBackPressed() },
                    isNavigateUpButtonEnabled = false
                ))
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    companion object {
        
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}