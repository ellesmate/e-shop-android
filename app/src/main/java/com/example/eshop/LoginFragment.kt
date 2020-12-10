package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.eshop.databinding.FragmentLoginBinding
import com.example.eshop.utilities.InjectorUtils
import com.example.eshop.viewmodels.LoginViewModel
import com.example.eshop.viewmodels.PasswordError
import com.example.eshop.viewmodels.UsernameError

class LoginFragment(private val onSuccess: () -> Unit) : Fragment() {

    private val viewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val login = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            try {
                viewModel.login(login, password) {
                    binding.usernameEditText.text?.clear()
                    binding.passwordEditText.text?.clear()
                    binding.passwordTextInput.error = ""

                    hide()
                    onSuccess()
                }
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