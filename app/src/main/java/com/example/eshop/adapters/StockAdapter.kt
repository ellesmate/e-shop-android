package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.databinding.StockListItemBinding
import com.example.eshop.models.Stock

class StockAdapter :
    ListAdapter<Stock, StockAdapter.ListViewHolder>(
        StockDiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return StockAdapter.ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.stock_list_item, parent, false
            )
        ).apply {
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder (
        val binding: StockListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: Stock) {
            binding.stockName.text = stock.description
        }
    }
}

private class StockDiffCallback : DiffUtil.ItemCallback<Stock>() {
    override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem == newItem
    }
}