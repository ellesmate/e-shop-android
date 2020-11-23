package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eshop.adapters.CartAdapter
import com.example.eshop.databinding.FragmentCartBinding
import com.example.eshop.models.CartItem

class CartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCartBinding.inflate(inflater, container, false)

        val cart = listOf(
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12),
                CartItem(1, "Naranda GAG110CNA", "Default", "$182.0", 182.0, listOf("https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"), 12)
        )

        val adapter = CartAdapter()
        binding.cartRecyclerView.adapter = adapter
        adapter.submitList(cart)

        binding.checkoutButton.setOnClickListener {
            it.findNavController().navigate(R.id.checkout_fragment)
        }

        return binding.root
    }
}