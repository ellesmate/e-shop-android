package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eshop.databinding.FragmentPaymentSuccessfulBinding

class PaymentSuccessfulFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPaymentSuccessfulBinding.inflate(inflater, container, false)

        binding.continueButton.setOnClickListener {
            val controller = it.findNavController()
            controller.navigateUp()
        }

        return binding.root
    }
}