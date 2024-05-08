package kg.fuankan.tezcargo.ui.cargo.add

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kg.fuankan.tezcargo.databinding.ActivityAddCargoBinding
import kg.fuankan.tezcargo.ui.base.BaseActivity

@AndroidEntryPoint
class AddCargoActivity : BaseActivity<AddCargoVM, ActivityAddCargoBinding>(
    AddCargoVM::class.java,
    ActivityAddCargoBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    fun showStringPickerDialog(items: Array<String>, onItemSelected: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
        builder.setItems(items) { _, which ->
            onItemSelected(items[which])
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, AddCargoActivity::class.java)
            context.startActivity(intent)
        }
    }
}