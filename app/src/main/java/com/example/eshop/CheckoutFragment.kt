package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshop.databinding.FragmentCheckoutBinding
import com.example.eshop.models.CustomerInfo
import com.example.eshop.utilities.InjectorUtils
import com.example.eshop.viewmodels.CheckoutViewModel
import com.google.android.material.snackbar.Snackbar

class CheckoutFragment : Fragment() {
    private val args: CheckoutFragmentArgs by navArgs()
    private val viewModel: CheckoutViewModel by viewModels {
        InjectorUtils.provideCheckoutViewModelFactory(requireContext())
    }

    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        binding.total.text = args.total

        binding.personalInformation.checkPersonalInfo.setOnClickListener {
            val firstName = binding.personalInformation.firstName.text.toString()
            val lastName = binding.personalInformation.lastName.text.toString()
            val email = binding.personalInformation.email.text.toString()
            val phone = binding.personalInformation.phone.text.toString()
            val address1 = binding.personalInformation.address1.text.toString()
            val address2 = binding.personalInformation.address2.text.toString()
            val city = binding.personalInformation.city.text.toString()
            val postcode = binding.personalInformation.postcode.text.toString()
//            val customerInfo = CustomerInfo(
//                    "qwerty",
//                    "asdfg",
//                    "a@gmawl.com",
//                    "375291234567",
//                    "asdfalskdfjklajsd",
//                    "",
//                    "Minsk",
//                    "220000"
//            )
            val customerInfo = CustomerInfo(
                    firstName,
                    lastName,
                    email,
                    phone,
                    address1,
                    address2,
                    city,
                    postcode
            )

            viewModel.addCustomerInfo(customerInfo)
        }

        binding.paymentBtn.setOnClickListener {
            val cardNumber = binding.paymentInformation.cardNumber
            val cvc = binding.paymentInformation.cvc
            val expDate = binding.paymentInformation.expiryDate

            if (cardNumber.isCardNumberValid && expDate.isDateValid) {
                viewModel.pay(
                        cardNumber.text.toString(),
                        cvc.text.toString(),
                        expDate.validatedDate!!.month,
                        expDate.validatedDate!!.year,
                        ::finish)
            }
        }

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        viewModel.isPaymentInfoEnable.observe(viewLifecycleOwner) {
            enableDisable(binding.personalInformation.root as ViewGroup, !it)
            enableDisable(binding.paymentInformation.root as ViewGroup, it)
            binding.paymentBtn.isEnabled = it
        }

        viewModel.snackBar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }
        }

        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
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

    private fun finish() {
        val controller = requireView().findNavController()
        controller.navigateUp()
        controller.navigateUp()
    }
}