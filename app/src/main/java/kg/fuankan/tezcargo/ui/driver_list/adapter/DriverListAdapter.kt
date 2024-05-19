package kg.fuankan.tezcargo.ui.driver_list.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.design2.chili2.extensions.setOnSingleClickListener
import kg.fuankan.tezcargo.R
import kg.fuankan.tezcargo.data.models.DriverInfo
import kg.fuankan.tezcargo.databinding.ItemDriverInfoBinding
import kg.fuankan.tezcargo.extensions.inflate
import kg.fuankan.tezcargo.extensions.setIsSurfaceClickable
import kg.fuankan.tezcargo.extensions.visible
import kg.fuankan.tezcargo.ui.base.BaseAdapter

class DriverListAdapter(private val listener: DriverListAdapterListener, private val isClickable: Boolean): BaseAdapter<DriverInfo>() {

    interface DriverListAdapterListener {
        fun onDriverClick(driver: DriverInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DriverListVH.create(parent, listener, isClickable)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val driver = currentList[position]
        (holder as DriverListVH).bind(driver)
    }
}

class DriverListVH private constructor(
    private val vb: ItemDriverInfoBinding,
    private val listener: DriverListAdapter.DriverListAdapterListener,
    private val isClickable: Boolean
) : RecyclerView.ViewHolder(vb.root) {


    fun bind(driver: DriverInfo) {
        itemView.apply {
            setIsSurfaceClickable(this@DriverListVH.isClickable)
            if (this@DriverListVH.isClickable) setOnSingleClickListener { listener.onDriverClick(driver) }
        }
        with(vb) {
            tvTitle.text = "Водитель №${driver.driverId}"
            tvSubtitle.text = "Имя: ${driver.name}\nТелефон: ${driver.phoneNumber}\nИНН: ${driver.pin}"
            tvSubtitle.visible()
            ivChevron.isVisible = isClickable
        }
    }

    companion object {

        fun create(parent: ViewGroup, listener: DriverListAdapter.DriverListAdapterListener, isClickable: Boolean): DriverListVH {
            val view = parent.inflate(R.layout.item_driver_info)
            val binding = ItemDriverInfoBinding.bind(view)
            return DriverListVH(binding, listener, isClickable)
        }
    }
}