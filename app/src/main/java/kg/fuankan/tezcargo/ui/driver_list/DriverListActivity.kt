package kg.fuankan.tezcargo.ui.driver_list

import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.databinding.ActivityDriverListBinding
import kg.fuankan.tezcargo.ui.base.BaseNavigatedActivity
import kg.fuankan.tezcargo.ui.base.BaseVM

@AndroidEntryPoint
class DriverListActivity : BaseNavigatedActivity<BaseVM, ActivityDriverListBinding>(
    BaseVM::class.java,
    R.id.driver_list_nav_host,
    ActivityDriverListBinding::inflate
) {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DriverListActivity::class.java)
            context.startActivity(intent)
        }
    }
}