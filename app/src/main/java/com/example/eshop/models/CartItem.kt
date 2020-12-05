package com.example.eshop.models

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("stockId")
    val stockId: Int,

    @SerializedName("name")
    val productName: String,

    val stockName: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("realValue")
    val realValue: Double,

    @SerializedName("images")
    val images: List<String>,

    @SerializedName("qty")
    var qty: Int
)