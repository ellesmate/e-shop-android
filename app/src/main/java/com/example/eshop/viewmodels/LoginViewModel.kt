package com.example.eshop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.models.Credentials
import com.example.eshop.services.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginService.login(Credentials(username, password))
            } catch (cause: Throwable) {
                println("error: ${cause.message}.")
            }
        }
    }
}