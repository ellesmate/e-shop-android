package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.MainFragmentDirections
import com.example.eshop.R
import com.example.eshop.databinding.ProductListItemBinding
import com.example.eshop.models.Product
import com.squareup.picasso.Picasso


class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ListViewHolder>(
        ProductDiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ProductAdapter.ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_list_item, parent, false
            )
        ).apply {
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder (
        val binding: ProductListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private fun navigateToProduct(view: View, slug: String) {
            val direction = MainFragmentDirections.actionMainFragmentToProductDetailFragment(slug)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Product) {
            binding.productName.text = item.name
            binding.productPrice.text = item.price

            Picasso.get()
                .load(item.images.first())
                .into(binding.productImage)

            binding.root.setOnClickListener {
                navigateToProduct(it, item.slug)
            }
        }
    }
}

private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}