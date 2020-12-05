package com.example.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.models.CartItem
import com.example.eshop.services.CartService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val cartService: CartService) : ViewModel() {

    private val _cart: MutableLiveData<MutableList<CartItem>> = MutableLiveData()
    private val _total: MutableLiveData<Double> = MutableLiveData(0.0)
    private val _spinner = MutableLiveData<Boolean>(false)
    private val _snackBar = MutableLiveData<String?>()

    val cart: LiveData<MutableList<CartItem>>
        get() = _cart

    val total: LiveData<Double>
        get() = _total

    val spinner: LiveData<Boolean>
        get() = _spinner

    val snackBar: LiveData<String?>
        get() = _snackBar

    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun fetchCart() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                try {
                    val data = cartService.getCart()
                    withContext(Dispatchers.Main) {
                        _cart.postValue(data as MutableList<CartItem>)
                        updateTotal(data)
                    }
                } catch (cause: Throwable) {
                    println(cause.message)
                    throw Error("Unable to load cart.", cause)
                }
            }
        }
    }

    fun removeOneFromCart(stockId: Int, onSuccess: (position: Int) -> Unit) {
        _cart.value?.let {cart ->
            val index = cart.indexOfFirst { it.stockId == stockId }
            if (index == -1) {
                throw Error("There is no this item.")
            }
            val cartItem = _cart.value!![index]

            launchDataLoad {
                withContext(Dispatchers.IO) {
                    try {
                        cartService.removeFromCart(stockId, 1)
                        withContext(Dispatchers.Main) {
                            cartItem.qty -= 1
                            if (cartItem.qty == 0) {
                                cart.removeAt(index)
                            }
                            updateTotal(_cart.value!!)
                            onSuccess(index)
                        }
                    } catch (cause: Throwable) {
                        throw Error("Unable to remove.", cause)
                    }
                }
            }
        }
    }

    fun addOneToCart(stockId: Int, onSuccess: () -> Unit) {
        val cartItem = _cart.value?.find { it.stockId == stockId }
        if (cartItem == null) {
            throw Error("There is no this item.")
        }

        launchDataLoad {
            withContext(Dispatchers.IO) {
                try {
                    cartService.addToCart(stockId, 1)

                    withContext(Dispatchers.Main) {
                        cartItem.qty += 1
                        updateTotal(_cart.value!!)
                        onSuccess()
                    }
                } catch (cause: Throwable) {
                    throw Error("Unable to add.", cause)
                }
            }
        }
    }

    private fun updateTotal(cart: List<CartItem>) {
        val sum = cart.sumByDouble { it.realValue * it.qty }
        _total.postValue(sum)
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            try {
                _spinner.value = true
                block()
            } catch (cause: Throwable) {
                println("Snackbar message: ${cause.message}")
                _snackBar.value = cause.message
            } finally {
                _spinner.value = false
            }
        }
    }
}