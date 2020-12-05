package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.eshop.adapters.ImageAdapter
import com.example.eshop.adapters.StockAdapter
import com.example.eshop.api.NetworkService
import com.example.eshop.databinding.FragmentProductDetailBinding
import com.example.eshop.viewmodels.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar

class ProductDetailFragment : Fragment() {

    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: FragmentProductDetailBinding

    private lateinit var stockAdapter: StockAdapter
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val network = NetworkService.getInstance(requireContext())
        viewModel = ProductDetailViewModel(network.productService, network.cartService)
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        stockAdapter = StockAdapter()
        imageAdapter = ImageAdapter()

        LinearSnapHelper().attachToRecyclerView(binding.imageCarousel)

        binding.apply {
            productStocks.adapter = stockAdapter
            imageCarousel.adapter = imageAdapter

            productPlusOne.setOnClickListener { viewModel.plusOne() }
            productMinusOne.setOnClickListener { viewModel.minusOne() }

            addToCart.setOnClickListener {
                viewModel.addToCart(
                    { it.findNavController().navigateUp() },
                    {}
                )
            }
        }

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            binding.apply {
                productName.text = product.name
                productDescription.text = product.description

                val priceNumber = viewModel.priceToNumber(product.price)
                productPrice.text =  viewModel.numberToPrice(priceNumber * viewModel.qty.value!!)
            }

            imageAdapter.submitList(product.images)
            stockAdapter.submitList(product.stocks)
        }

        viewModel.snackBar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }
        }

        viewModel.qty.observe(viewLifecycleOwner) {qty ->
            binding.productQty.text = qty.toString()
            viewModel.product.value?.let {
                val priceNumber = viewModel.priceToNumber(it.price)
                binding.productPrice.text = viewModel.numberToPrice(priceNumber * qty)
            }
        }

        val slug = args.slug
        viewModel.fetchProduct(slug)
    }
}