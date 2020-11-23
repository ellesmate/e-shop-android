package com.example.eshop.models

data class CartItem(
    val stockId: Int,
    val productName: String,
    val stockName: String,
    val value: String,
    val realValue: Double,
    val images: List<String>,
    val qty: Int
)