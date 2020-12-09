package com.example.eshop.utilities

import android.content.Context
import com.example.eshop.api.NetworkService
import com.example.eshop.viewmodels.*

object InjectorUtils {
    private fun getLoginService(context: Context) = NetworkService.getInstance(context).loginService

    private fun getProductService(context: Context) = NetworkService.getInstance(context).productService

    private fun getCartService(context: Context) = NetworkService.getInstance(context).cartService

    private fun getPaymentService(context: Context) = NetworkService.getInstance(context).paymentService

    fun provideCartViewModelFactory(
            context: Context
    ) : CartViewModelFactory {
        return CartViewModelFactory(getCartService(context))
    }

    fun provideCheckoutViewModelFactory(
            context: Context
    ) : CheckoutViewModelFactory {
        return CheckoutViewModelFactory(context, getPaymentService(context))
    }

    fun provideLoginViewModelFactory(
            context: Context
    ) : LoginViewModelFactory {
        return LoginViewModelFactory(getLoginService(context))
    }

    fun provideMainViewModelFactory(
            context: Context
    ) : MainViewModelFactory {
        return MainViewModelFactory(getProductService(context))
    }

    fun provideProductDetailViewModelFactory(
            context: Context
    ) : ProductDetailViewModelFactory {
        return ProductDetailViewModelFactory(getProductService(context), getCartService(context))
    }
}