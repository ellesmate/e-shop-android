package com.example.eshop.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.models.CustomerInfo
import com.example.eshop.services.PaymentService
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import kotlinx.coroutines.*

class CheckoutViewModel(
        private val context: Context,
        private val paymentService: PaymentService)
    : ViewModel() {

    private val _isPaymentInfoEnable: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _spinner = MutableLiveData<Boolean>(false)
    private val _snackBar = MutableLiveData<String?>()

    val isPaymentInfoEnable: LiveData<Boolean>
        get() = _isPaymentInfoEnable

    val spinner: LiveData<Boolean>
        get() = _spinner

    val snackBar: LiveData<String?>
        get() = _snackBar

    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun addCustomerInfo(customerInfo: CustomerInfo) {
        viewModelScope.launch(Dispatchers.Main) {
            _spinner.postValue(true)
            try {
                withContext(Dispatchers.IO) {
                    paymentService.addCustomerInfo(customerInfo)
                }

                _isPaymentInfoEnable.postValue(true)
            } catch (cause: Throwable) {
                println("Error: ${cause.message}")
            } finally {
                _spinner.postValue(false)
            }
        }
    }

    fun pay(cardNumber: String, cvc: String, month: Int, year: Int, onSuccess: () -> Unit) {
        _spinner.postValue(true)
        val card = Card.create(cardNumber, month, year, cvc)
        val stripe = Stripe(context, "pk_test_51HT3DWEKEWuLXH6HeFBV7LfolPf3KVjtJSvjRzcWagR30jGf3V9CmZbwyaqWzAdSwFGVTKPSAmEvuAahSDtsyENq002bIafYqM")
        stripe.createCardToken(
                card,
                callback = object : ApiResultCallback<Token> {
                    override fun onSuccess(result: Token) {
                        viewModelScope.launch(Dispatchers.Main) {
                            try {
                                withContext(Dispatchers.IO) {
                                    paymentService.pay(result.id)
                                }
                                _snackBar.postValue("Success!")
                                onSuccess()
                            } catch (error: Throwable) {
                                println("Error: ${error.message}")
                                _snackBar.postValue("Payment failed!")
                            } finally {
                                _spinner.postValue(false)
                            }
                        }
                    }

                    override fun onError(e: Exception) {
                    }
                }
        )
    }
}