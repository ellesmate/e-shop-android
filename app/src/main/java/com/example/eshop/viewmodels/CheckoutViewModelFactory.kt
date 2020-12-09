package com.example.eshop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eshop.services.PaymentService

class CheckoutViewModelFactory (
        private val context: Context,
        private val paymentService: PaymentService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CheckoutViewModel(context, paymentService) as T
    }
}