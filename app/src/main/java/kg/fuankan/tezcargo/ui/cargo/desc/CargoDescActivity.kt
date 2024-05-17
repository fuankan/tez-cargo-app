package kg.fuankan.tezcargo.ui.cargo.desc

import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.ActivityCargoDescBinding
import kg.fuankan.tezcargo.ui.base.BaseActivity

@AndroidEntryPoint
class CargoDescActivity : BaseActivity<CargoDescVM, ActivityCargoDescBinding>(
    CargoDescVM::class.java,
    ActivityCargoDescBinding::inflate
) {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CargoDescActivity::class.java)
            context.startActivity(intent)
        }
    }
}