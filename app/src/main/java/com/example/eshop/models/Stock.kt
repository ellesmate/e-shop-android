package com.example.eshop.models

import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("qty")
    val qty: Int
)