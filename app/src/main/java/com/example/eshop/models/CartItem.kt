package com.example.eshop.models

data class CartItem(
    val stockId: Int,
    val name: String,
    val value: String,
    val realValue: Double,
    val images: List<String>,
    val qty: Int
)