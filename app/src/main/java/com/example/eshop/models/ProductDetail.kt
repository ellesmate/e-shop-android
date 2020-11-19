package com.example.eshop.models

data class ProductDetail(val name: String, val description: String, val slug: String, val price: String, val images: List<String>, val stocks: List<Stock>)