package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.databinding.CategoryListItemBinding
import com.example.eshop.models.Category
import com.squareup.picasso.Picasso

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.ListViewHolder>(
        CategoryDiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.category_list_item, parent, false
            )
        ).apply {
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder (
        val binding: CategoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.categoryText.text = item.name

            Picasso.get()
                .load(item.image)
                .into(binding.categoryImage)
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}