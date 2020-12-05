package com.example.eshop.models

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("value")
    val price: String,

    @SerializedName("images")
    val images: List<String>,

    @SerializedName("stock")
    val stocks: List<Stock>
)