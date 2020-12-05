package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.eshop.adapters.CartAdapter
import com.example.eshop.api.NetworkService
import com.example.eshop.databinding.FragmentCartBinding
import com.example.eshop.models.CartItem
import com.example.eshop.viewmodels.CartViewModel
import com.google.android.material.snackbar.Snackbar

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        viewModel = CartViewModel(NetworkService.getInstance(requireContext()).cartService)

//        val cart = listOf(
//                CartItem(1, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(2, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(3, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(4, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(5, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(6, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
//                CartItem(7, "Naranda GAG110CNA", "Default", "$182.00", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12)
//        )

        cartAdapter = CartAdapter(requireContext(), viewModel::addOneToCart, viewModel::removeOneFromCart)
        binding.cartRecyclerView.adapter = cartAdapter

        binding.checkoutButton.setOnClickListener {
            val direction = CartFragmentDirections.actionCartFragmentToCheckoutFragment()
            it.findNavController().navigate(direction)
        }

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        viewModel.cart.observe(viewLifecycleOwner) {
            cartAdapter.submitList(it)
        }

        viewModel.total.observe(viewLifecycleOwner) {
            binding.cartTotal.text = "$ %.2f".format(it)
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

        viewModel.fetchCart()
    }
}