package com.example.eshop.viewmodels

import androidx.lifecycle.*
import com.example.eshop.models.Category
import com.example.eshop.models.Product
import com.example.eshop.services.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val productService: ProductService
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    private val _categories = MutableLiveData<List<Category>>(
        listOf(
            Category("acoustic", "https://e-shopdotnet.herokuapp.com/images/acoustic_category.jpg"),
            Category("electric", "https://e-shopdotnet.herokuapp.com/images/electric_category.jpg"),
            Category("bass", "https://e-shopdotnet.herokuapp.com/images/bass_category.jpg")
        )
    )
    private val _spinner = MutableLiveData<Boolean>(false)
    private val _snackBar = MutableLiveData<String?>()

    val products: LiveData<List<Product>>
        get() = _products

    val categories: LiveData<List<Category>>
        get() = _categories

    val spinner: LiveData<Boolean>
        get() = _spinner

    val snackBar: LiveData<String?>
        get() = _snackBar

    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun getProducts() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val data = try {
                    productService.getProducts()
//                    productService.getProducts(0, 50)
                } catch (cause: Throwable) {
                    println("My Error.")
                    return@withContext
                }

                println(data)

                withContext(Dispatchers.Main) {
                    _products.postValue(data)
                }
            }
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            try {
                _spinner.value = true
                block()
            } catch (cause: Throwable) {
                _snackBar.value = cause.message
            } finally {
                _spinner.value = false
            }
        }
    }
}