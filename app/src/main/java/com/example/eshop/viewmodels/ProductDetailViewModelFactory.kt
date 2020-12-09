package com.example.eshop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eshop.services.CartService
import com.example.eshop.services.ProductService

class ProductDetailViewModelFactory (
        private val productService: ProductService,
        private val cartService: CartService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productService, cartService) as T
    }
}