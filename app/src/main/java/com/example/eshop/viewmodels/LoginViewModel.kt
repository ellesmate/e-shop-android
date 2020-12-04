package com.example.eshop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshop.models.Credentials
import com.example.eshop.services.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    fun login(username: String, password: String) {
        val credentials = Credentials(username, password)
        validate(credentials)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginService.login(Credentials(username, password))
            } catch (cause: Throwable) {
                println("error: ${cause.message}.")
            }
        }
    }

    private fun validate(credentials: Credentials) {
        if (credentials.username.length < 2) {
            throw UsernameError("Username has to be more than 2.", null)
        }

        if (credentials.password.length <= 6) {
            throw PasswordError("Password has to be more than 6.", null)
        }
    }
}

class UsernameError(message: String, cause: Throwable?): Throwable(message, cause)

class PasswordError(message: String, cause: Throwable?): Throwable(message, cause)