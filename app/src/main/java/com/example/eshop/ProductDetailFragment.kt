package com.example.eshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.eshop.adapters.ImageAdapter
import com.example.eshop.adapters.StockAdapter
import com.example.eshop.databinding.FragmentProductDetailBinding
import com.example.eshop.models.ProductDetail
import com.example.eshop.models.Stock
import com.squareup.picasso.Picasso

class ProductDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        val product = ProductDetail(
                "Naranda GAG110CNA",
                "Количество струн: 6. Форма: джаз. Верхняя дека: ель. Нижняя дека и обечайка: махагонь. Накладка грифа: палисандр. Цвет верхней деки: натуральный. Покрытие: глянцевое. Механизм крепления струн: металлический держатель. Звукосниматели: 1 single. Элементы регулировки: звук и тон. Форма резонаторных отверстий: f-образная.",
                "naranda-gag110cna",
                "$ 182.00",
                listOf(
                        "https://e-shopdotnet.herokuapp.com/images/guitar_1.jpg",
                        "https://e-shopdotnet.herokuapp.com/images/guitar_2.jpg"
                ),
                listOf(
                        Stock(1, "Default", 100),
                        Stock(5, "Green", 100)
                )
        )

        val stockAdapter = StockAdapter()
        val imageAdapter = ImageAdapter()

        binding.apply {
            productName.text = product.name
            productDescription.text = product.description
            productPrice.text = product.price
            productStocks.adapter = stockAdapter

            LinearSnapHelper().attachToRecyclerView(imageCarousel)
            imageCarousel.adapter = imageAdapter
        }

        stockAdapter.submitList(product.stocks)
        imageAdapter.submitList(product.images)

        return binding.root
    }
}

//CarouselView carouselView;
//
//int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_sample_carousel_view);
//
//    carouselView = (CarouselView) findViewById(R.id.carouselView);
//    carouselView.setPageCount(sampleImages.length);
//
//    carouselView.setImageListener(imageListener);
//}
//
//ImageListener imageListener = new ImageListener() {
//    @Override
//    public void setImageForPosition(int position, ImageView imageView) {
//        imageView.setImageResource(sampleImages[position]);
//    }
//};