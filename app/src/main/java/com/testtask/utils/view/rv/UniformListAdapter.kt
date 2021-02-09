package com.testtask.utils.view.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic RV adapter for displaying uniform lists.
 * @param itemLayout item layout resource id. Note: variable with the name "item" (and concrete type of T) should be introduced in this layout
 * @param getId optional function retrieving any unique item id for DiffUtil.ItemCallback. If not specified, items will be compared by equals()
 * @param onItemClick optional item click callback
 */
class UniformListAdapter<T>(
    @LayoutRes private val itemLayout: Int,
    getId: ((item: T) -> Any)? = null,
    private val onItemClick: (item: T) -> Unit = {},
) : ListAdapter<T, UniformListAdapter.ViewHolder>(DiffCallback(getId)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayout,
                parent,
                false
            )
        )
            .apply {
                binding.root.setOnClickListener {
                    val safePosition =
                        adapterPosition.takeIf { it in 0 until this@UniformListAdapter.itemCount }
                            ?: return@setOnClickListener
                    onItemClick(getItem(safePosition))
                }
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val safePosition = holder.adapterPosition.takeIf { it in 0 until this.itemCount } ?: return
        holder.binding.setVariable(BR.item, getItem(safePosition))
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("DiffUtilEquals")
    class DiffCallback<T>(private val getId: ((item: T) -> Any)?) : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            getId?.let { it(oldItem) == it(newItem) } ?: oldItem == newItem

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    }
}