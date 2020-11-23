package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.databinding.CartListItemBinding
import com.example.eshop.models.CartItem
import com.squareup.picasso.Picasso

class CartAdapter :
    ListAdapter<CartItem, CartAdapter.ListViewHolder>(
        CartItemDiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.cart_list_item, parent, false
                )
        ).apply {
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder (
            val binding: CartListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                cartItemName.text = item.productName
                cartItemPrice.text = item.value
                cartItemQty.text = "x ${item.qty}"

                Picasso.get().load(item.images.first()).into(cartItemImage)
            }
        }
    }
}

private class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.stockId == newItem.stockId
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}