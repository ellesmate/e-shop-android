package com.example.eshop.models

import com.google.gson.annotations.SerializedName

data class CustomerInfo(
        @SerializedName("firstName")
        val firstName: String,

        @SerializedName("lastName")
        val lastName: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("phoneNumber")
        val phoneNumber: String,

        @SerializedName("address1")
        val address1: String,

        @SerializedName("address2")
        val address2: String,

        @SerializedName("city")
        val city: String,

        @SerializedName("postCode")
        val postCode: String
)