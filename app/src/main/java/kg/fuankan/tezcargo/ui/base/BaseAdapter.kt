package kg.fuankan.tezcargo.ui.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

// todo: add payload logic, if viewHolders get complex
abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val asyncListDiffer by lazy {
        AsyncListDiffer(this, DiffCallBack())
    }

    private inner class DiffCallBack : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return this@BaseAdapter.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return this@BaseAdapter.areContentsTheSame(oldItem, newItem)
        }
    }

    protected open fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    protected open fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    fun submitList(newList: List<T>) = asyncListDiffer.submitList(newList)

    override fun getItemCount(): Int = asyncListDiffer.currentList.count()

    protected val currentList: List<T>
        get() = asyncListDiffer.currentList
}