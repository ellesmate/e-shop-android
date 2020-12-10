package com.example.eshop.adapters

import android.content.Context
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso

class CartAdapter(
        private val context: Context,
        private val addOne: (stockId: Int, onSuccess: () -> Unit) -> Unit,
        private val removeOne: (stockId: Int, onSuccess: (position: Int) -> Unit) -> Unit
) :
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

    inner class ListViewHolder (
            val binding: CartListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                cartItemName.text = item.productName
                cartItemPrice.text = item.value
                updateQty(item)

                removeOneBtn.setOnClickListener {
                    if (item.qty == 1) {
                        // Ask to delete
                        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Shop)
                                .setMessage("Remove item?")
                                .setPositiveButton("Remove") { dialog, which ->
                                    removeOne(item.stockId) { index ->
                                        this@CartAdapter.notifyItemRemoved(index)
                                    }
                                }
                                .setNegativeButton("Cancel") { dialog, which ->

                                }
                                .show()
                    } else {
                        removeOne(item.stockId) {
                            updateQty(item)
                        }
                    }
                }

                addOneBtn.setOnClickListener {
                    addOne(item.stockId) { updateQty(item) }
                }

                val base_url = "https://e-shopdotnet.herokuapp.com"

                Picasso.get().load(base_url + item.images.first()).into(cartItemImage)
            }
        }

        private fun updateQty(item: CartItem) {
            binding.cartItemQty.text = "${item.qty}"
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