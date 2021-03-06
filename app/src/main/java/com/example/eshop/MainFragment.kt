package com.example.eshop

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.adapters.CategoryAdapter
import com.example.eshop.adapters.ProductAdapter
import com.example.eshop.databinding.FragmentMainBinding
import com.example.eshop.utilities.InjectorUtils
import com.example.eshop.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory(requireContext())
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var loginFragment: LoginFragment
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    private val productLayoutAnimation = ProductLayoutAnimation()
    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(productLayoutAnimation.shown) {
        override fun handleOnBackPressed() {
            binding.productList.smoothScrollTo(0, 0)
            productLayoutAnimation.action()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        loginFragment = LoginFragment(viewModel::getMe)
        binding.changeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        productLayoutAnimation.defaultBottomPadding = binding.productList.paddingBottom
        productLayoutAnimation.action(0)

        categoryAdapter = CategoryAdapter(::categoryClick)
        productAdapter = ProductAdapter()

        binding.categoryRecyclerview.adapter = categoryAdapter
        binding.viewAllText.setOnClickListener {
            productLayoutAnimation.action()
        }

        binding.productRecyclerview.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.productRecyclerview.addItemDecoration(GridItemDecoration(
                resources.getDimensionPixelSize(R.dimen.productRowMargin),
                resources.getDimensionPixelSize(R.dimen.productColumnsMargin)))
        binding.productRecyclerview.adapter = productAdapter

        binding.productList.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (productLayoutAnimation.shown && oldScrollY < scrollY)
            {
                productLayoutAnimation.action()
            }
        }

        binding.cartButton.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToCartFragment()
            it.findNavController().navigate(direction)
        }

        subscribeUi()

        return binding.root
    }

    private fun categoryClick(category: String) : Boolean {
        if (viewModel.category.value == null) {
            viewModel.category.postValue(category)
            return true
        }

        viewModel.category.value?.let {
            if (it != category) {
                viewModel.category.postValue(category)
                return true
            }
        }

        viewModel.category.postValue(null)
        return false
    }

    private fun subscribeUi() {
        viewModel.getMe()

        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }

        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }

        viewModel.me.observe(viewLifecycleOwner) {
            it?.let {
                binding.username.text = it
            }
        }

        viewModel.snackBar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }
        }

        viewModel.category.observe(viewLifecycleOwner) {
            if (it == null) {
                viewModel.getProducts()
            }

            it?.let {
                viewModel.getProductsByCategory(it)
            }
        }

        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onPause() {
        onBackPressedCallback.isEnabled = false
        super.onPause()
    }

    override fun onResume() {
        onBackPressedCallback.isEnabled = true
        super.onResume()
    }

    inner class ProductLayoutAnimation() {
        var shown = false
        var defaultBottomPadding = 0
        private val interpolator = AccelerateDecelerateInterpolator()

        fun action(duration: Long = 500)
        {
            shown = !shown
            onBackPressedCallback.isEnabled = !shown
            val animatorSet = AnimatorSet()
            animatorSet.removeAllListeners()
            animatorSet.end()
            animatorSet.cancel()

            val translateY = requireContext().resources.getDimensionPixelSize(R.dimen.productListMarginTop)

            if (!shown)
                binding.productList.setPadding(0, 0, 0, defaultBottomPadding)

            val animator = ObjectAnimator.ofFloat(binding.productList, "translationY", (if (shown) translateY else 0).toFloat())
            animator.duration = duration
            animator.interpolator = interpolator

            animatorSet.play(animator)
            animator.start()
            animator.doOnEnd {
                if (shown)
                    binding.productList.setPadding(0, 0, 0, translateY + defaultBottomPadding)
            }
        }
    }
}