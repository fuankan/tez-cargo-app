package kg.fuankan.tezcargo.ui.main.admin.packages.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.design2.chili2.extensions.setOnSingleClickListener
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.databinding.ItemCargoMainBinding
import kg.fuankan.tezcargo.extensions.inflate
import kg.fuankan.tezcargo.ui.base.BaseAdapter

class CargoListAdapter(private val listener: CargoClickListener) : BaseAdapter<CargoDesc>() {

    interface CargoClickListener {
        fun onCargoClick(cargo: CargoDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CargoListVH.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cargo = currentList[position]
        (holder as CargoListVH).bind(cargo)
    }
}

class CargoListVH(
    private val vb: ItemCargoMainBinding,
    private val listener: CargoListAdapter.CargoClickListener
) : RecyclerView.ViewHolder(vb.root) {

    fun bind(cargo: CargoDesc) {
        with(vb.icvCargo) {
            cargo.deliveryStatus?.status?.let { setTitle(it) }
            setSubtitle("Груз №${cargo.deliveryId}")
        }
        itemView.setOnSingleClickListener {
            listener.onCargoClick(cargo)
        }
    }

    companion object {

        fun create(parent: ViewGroup, listener: CargoListAdapter.CargoClickListener): CargoListVH {
            val view = parent.inflate(R.layout.item_cargo_main)
            val binding = ItemCargoMainBinding.bind(view)
            return CargoListVH(binding, listener)
        }
    }
}


