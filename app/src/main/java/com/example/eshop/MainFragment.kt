package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshop.adapters.CategoryAdapter
import com.example.eshop.adapters.ProductAdapter
import com.example.eshop.databinding.FragmentMainBinding
import com.example.eshop.models.Category
import com.example.eshop.models.Product

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val categories = listOf<Category>(
            Category("acoustic", "https://e-shopdotnet.herokuapp.com/images/acoustic_category.jpg"),
            Category("electric", "https://e-shopdotnet.herokuapp.com/images/electric_category.jpg"),
            Category("bass", "https://e-shopdotnet.herokuapp.com/images/bass_category.jpg")
        )
        val products = listOf<Product>(
            Product("Naranda-GAG110CNA", "Naranda GAG110CNA", "https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", 182.0),
            Product("MD SDG653", "MD SDG653", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg", 454.54),
            Product("Cort Sunset NY", "Cort Sunset NY", "https://e-shopdotnet.herokuapp.com/images/guitar_3.jpg", 915.20)
        )

        val categoryAdapter = CategoryAdapter()
        val productAdapter = ProductAdapter()

        binding.categoryRecyclerview.adapter = categoryAdapter
        binding.productRecyclerview.adapter = productAdapter

        categoryAdapter.submitList(categories)
        productAdapter.submitList(products)

        return binding.root
    }
}