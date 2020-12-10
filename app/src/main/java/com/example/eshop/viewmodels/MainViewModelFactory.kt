package com.example.eshop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eshop.services.LoginService
import com.example.eshop.services.ProductService

class MainViewModelFactory (
        private val loginService: LoginService,
        private val productService: ProductService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(loginService, productService) as T
    }
}