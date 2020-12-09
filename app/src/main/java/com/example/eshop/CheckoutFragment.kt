package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.eshop.api.NetworkService
import com.example.eshop.databinding.FragmentCheckoutBinding
import com.example.eshop.models.CustomerInfo
import com.example.eshop.services.PaymentService
import com.stripe.android.*
import com.stripe.android.model.*
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.coroutines.*

class CheckoutFragment : Fragment() {
    private var paymentSession: PaymentSession? = null
    private lateinit var paymentService: PaymentService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        paymentService = NetworkService.getInstance(requireContext()).paymentService

        if (binding.personalInformation.root is ViewGroup) {
            println("Is View group.")

            enableDisable(binding.paymentInformation.root as ViewGroup, false)
        }

        binding.personalInformation.checkPersonalInfo.setOnClickListener {
            val firstName = binding.personalInformation.firstName.text.toString()
            val lastName = binding.personalInformation.lastName.text.toString()
            val email = binding.personalInformation.email.text.toString()
            val phone = binding.personalInformation.phone.text.toString()
            val address1 = binding.personalInformation.address1.text.toString()
            val address2 = binding.personalInformation.address2.text.toString()
            val city = binding.personalInformation.city.text.toString()
            val postcode = binding.personalInformation.postcode.text.toString()
            val customerInfo = CustomerInfo(
                    "qwerty",
                    "asdfg",
                    "a@gmawl.com",
                    "375291234567",
                    "asdfalskdfjklajsd",
                    "",
                    "Minsk",
                    "220000"
            )
//            val customerInfo = CustomerInfo(
//                    firstName,
//                    lastName,
//                    email,
//                    phone,
//                    address1,
//                    address2,
//                    city,
//                    postcode
//            )

            MainScope().launch(Dispatchers.IO) {
                try {
                    paymentService.addCustomerInfo(customerInfo)
                    withContext(Dispatchers.Main) {
                        enableDisable(binding.personalInformation.root as ViewGroup, false)
                        enableDisable(binding.paymentInformation.root as ViewGroup, true)
                    }
                } catch (cause: Throwable) {
                    println("Error: ${cause.message}")
                }
            }
        }

        binding.paymentBtn.setOnClickListener {
            val cardNumber = binding.paymentInformation.cardNumber.text.toString()
            val cvv = binding.paymentInformation.cvc.text.toString()

            val card = Card.create("4242424242424242", 10, 2022, "123")
            val stripe = Stripe(requireContext(), "pk_test")
            stripe.createCardToken(
                    card,
                    callback = object : ApiResultCallback<Token> {
                        override fun onSuccess(result: Token) {
                            GlobalScope.launch(Dispatchers.IO) {
                                try {
                                    paymentService.pay(result.id)
                                } catch (error: Throwable) {
                                    println("error:")
                                    println(error.message)
                                }
                            }
                        }

                        override fun onError(e: Exception) {
                        }
                    }
            )
        }

        return binding.root
    }

    private fun enableDisable(group: ViewGroup, enable: Boolean) {
        group.isEnabled = enable
        group.isActivated = enable

        group.children.forEach {
            if (it is ViewGroup) {
                enableDisable(it, enable)
            } else {
                it.isEnabled = enable
                it.isActivated = enable
            }
        }
    }
}