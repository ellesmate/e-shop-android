package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshop.api.NetworkService
import com.example.eshop.databinding.FragmentLoginBinding
import com.example.eshop.viewmodels.LoginViewModel

class LoginFragment(val cancel: () -> Unit) : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = LoginViewModel(NetworkService.getInstance(requireContext()).loginService)
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            viewModel.login("Admin", "password")
            println("Clicked login.")
        }

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .remove(this)
                .commit()

            this@LoginFragment.cancel()
        }

        return binding.root
    }
}