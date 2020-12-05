package com.example.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.models.ProductDetail
import com.example.eshop.models.Stock
import com.example.eshop.services.CartService
import com.example.eshop.services.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat

class ProductDetailViewModel(
    private val productService: ProductService,
    private val cartService: CartService
) : ViewModel() {

    private val _product = MutableLiveData<ProductDetail>()
    private val _snackBar: MutableLiveData<String?> = MutableLiveData()
    private val _qty: MutableLiveData<Int> = MutableLiveData(1)

    val currentStock = MutableLiveData<Stock?>()

    val product: LiveData<ProductDetail>
        get() = _product

    val snackBar: LiveData<String?>
        get() = _snackBar

    val qty: LiveData<Int>
        get() = _qty

    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun fetchProduct(slug: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = productService.getProduct(slug)

                withContext(Dispatchers.Main) {
                    println(data)
                    _product.postValue(data)
                    currentStock.postValue(data.stocks.first())
                }
            } catch (error: Throwable) {
                _snackBar.postValue(error.message)
            }
        }
    }

    fun plusOne() {
        _qty.value?.let {prev ->
            _qty.postValue(prev + 1)
        }
    }

    fun minusOne() {
        _qty.value?.let {prev ->
            if (prev != 1) {
                _qty.postValue(prev - 1)
            }
        }
    }

    fun addToCart(onSuccess: () -> Unit, onError: () -> Unit) {
        val qty = _qty.value

        if (qty != null && qty < 1) {
            return
        }

        currentStock.value?.let { stock ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    cartService.addToCart(stock.id, qty!!)
                    onSuccess()
                } catch (error: Throwable) {
                    _snackBar.postValue(error.message)
                    onError()
                }
            }
        }
    }

    fun priceToNumber(price: String): Double {
        val nf = NumberFormat.getInstance()
        return nf.parse(price.trimStart('$', ' '))?.toDouble() ?: throw NullPointerException()
    }

    fun numberToPrice(price: Double): String = "$ %.2f".format(price)
}