package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.View
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

class CategoryAdapter(private val clickCategory: (category: String) -> Boolean) :
    ListAdapter<Category, CategoryAdapter.ListViewHolder>(
        CategoryDiffCallback()
    ){

    private var currect: ListViewHolder? = null

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

    inner class ListViewHolder (
        val binding: CategoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.categoryText.text = item.name

            binding.root.setOnClickListener {
                if (clickCategory(item.name)) {
                    currect?.let {
                        setScale(it.binding.root, 1.0f)
                    }

                    currect = this
                    setScale(binding.root, 1.07f)
                } else {
                    setScale(binding.root, 1.0f)
                }
            }

            Picasso.get()
                .load(item.image)
                .into(binding.categoryImage)
        }

        fun setScale(view: View, scale: Float) {
            view.scaleX = scale
            view.scaleY = scale
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