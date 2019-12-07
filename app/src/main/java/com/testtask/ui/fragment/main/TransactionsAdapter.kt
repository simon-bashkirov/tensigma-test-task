package com.testtask.ui.fragment.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.testtask.databinding.ItemTransactionBinding
import com.testtask.ui.model.TransactionItem

class TransactionsAdapter :
    ListAdapter<TransactionItem, TransactionsAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemTransactionBinding.inflate(layoutInflater, parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, ignoredUnsafePosition: Int) {
        val safePosition = holder.adapterPosition
        if (safePosition in 0 until itemCount) {
            holder.bind(getItem(safePosition))
        }
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionItem) {
            binding.setItem(item)
            binding.executePendingBindings()
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionItem>() {

        override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TransactionItem, newItem: TransactionItem) =
            oldItem.hashOutput == newItem.hashOutput
    }
}