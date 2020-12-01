package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.eshop.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

class LoginFragment(val cancel: () -> Unit) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

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