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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.adapters.CategoryAdapter
import com.example.eshop.adapters.ProductAdapter
import com.example.eshop.databinding.FragmentMainBinding
import com.example.eshop.models.Category
import com.example.eshop.models.Product

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val categories = listOf(
            Category("acoustic", "https://e-shopdotnet.herokuapp.com/images/acoustic_category.jpg"),
            Category("electric", "https://e-shopdotnet.herokuapp.com/images/electric_category.jpg"),
            Category("bass", "https://e-shopdotnet.herokuapp.com/images/bass_category.jpg")
        )
        val products = listOf(
            Product("Naranda-GAG110CNA", "Naranda GAG110CNA", "https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg", 182.0),
            Product("MD SDG653", "MD SDG653", "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg", 454.54),
            Product("Cort Sunset NY", "Cort Sunset NY", "https://e-shopdotnet.herokuapp.com/images/guitar_3.jpg", 915.20)
        )

        val categoryAdapter = CategoryAdapter()
        val productAdapter = ProductAdapter()

        binding.categoryRecyclerview.adapter = categoryAdapter
        binding.viewAllText.setOnClickListener {
            action(binding)
        }

        binding.productRecyclerview.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.productRecyclerview.addItemDecoration(GridItemDecoration(
                resources.getDimensionPixelSize(R.dimen.productRowMargin),
                resources.getDimensionPixelSize(R.dimen.productColumnsMargin)))
        binding.productRecyclerview.adapter = productAdapter

        categoryAdapter.submitList(categories)
        productAdapter.submitList(products)

        onBackPressedCallback = object : OnBackPressedCallback(shown) {
            override fun handleOnBackPressed() {
                binding.productList.smoothScrollTo(0, 0);
                action(binding)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        binding.productList.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
//            println("Old: $oldScrollY, new: $scrollY")
            if (shown && oldScrollY < scrollY)
            {
                action(binding)
            }
        }

        defaultBottomPadding = binding.productList.paddingBottom
        action(binding, 0)

        return binding.root
    }

    private var shown = false
    private val interpolator = AccelerateDecelerateInterpolator()
    private var defaultBottomPadding = 0

    private fun action(binding: FragmentMainBinding, duration: Long = 500)
    {
        shown = !shown
        onBackPressedCallback.isEnabled = !shown
        val animatorSet = AnimatorSet()
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = context?.resources?.getDimensionPixelSize(R.dimen.productListMarginTop)!!

        if (!shown)
            binding.productList.setPadding(0, 0, 0, defaultBottomPadding)

        val animator = ObjectAnimator.ofFloat(binding.productList, "translationY", (if (shown) translateY else 0).toFloat())
        animator.duration = duration
        if (interpolator != null) {
            animator.interpolator = interpolator
        }

        animatorSet.play(animator)
        animator.start()
        animator.doOnEnd {
            if (shown)
                binding.productList.setPadding(0, 0, 0, translateY + defaultBottomPadding)
        }
    }
}