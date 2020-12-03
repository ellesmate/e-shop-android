package com.example.eshop.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("slug")
    val slug: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("value")
    val price: String,

    @SerializedName("images")
    val images: List<String>,

    @SerializedName("stockCount")
    val count: Int
)
