package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshop.api.NetworkService
import com.example.eshop.databinding.FragmentLoginBinding
import com.example.eshop.viewmodels.LoginViewModel
import com.example.eshop.viewmodels.PasswordError
import com.example.eshop.viewmodels.UsernameError

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = LoginViewModel(NetworkService.getInstance(requireContext()).loginService)
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val login = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            try {
                viewModel.login(login, password)

                binding.usernameEditText.text?.clear()
                binding.passwordEditText.text?.clear()
                binding.passwordTextInput.error = ""

                hide()
            } catch (error: UsernameError) {
                println(error.message)
            } catch (error: PasswordError) {
                binding.passwordTextInput.error = error.message
            } catch (cause: Throwable) {
                binding.passwordTextInput.error = cause.message
            }
        }

        binding.cancelButton.setOnClickListener {
            hide()
        }

        return binding.root
    }

    private fun hide() {
        parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
    }
}