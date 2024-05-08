package kg.fuankan.tezcargo.ui.cargo.desc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.R
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