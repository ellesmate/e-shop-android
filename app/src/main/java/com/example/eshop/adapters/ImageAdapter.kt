package com.example.eshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.databinding.CarouselImageItemBinding
import com.squareup.picasso.Picasso

class ImageAdapter :
    ListAdapter<String, ImageAdapter.ListViewHolder>(
        ImageDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ImageAdapter.ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.carousel_image_item, parent, false
            )
        ).apply {
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder (
        val binding: CarouselImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            val base_url = "https://e-shopdotnet.herokuapp.com"

            Picasso.get()
                .load(base_url + image)
                .placeholder(R.drawable.guitar_1)
                .into(binding.image)
        }
    }
}

private class ImageDiffCallback : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}