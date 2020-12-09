package com.example.eshop.services

import com.example.eshop.models.CustomerInfo
import retrofit2.http.*

interface PaymentService {
    @Headers("Content-Type: application/json")
    @POST("key/{version}")
    suspend fun createEphemeralKey(
        @Path("version") apiVersion: String
    ) : Any

    @POST("checkout/addCustomerInfo")
    suspend fun addCustomerInfo(
        @Body customerInfo: CustomerInfo
    )

//    @FormUrlEncoded
//    @POST("checkout/pay")
//    suspend fun pay(
//            @Field("stripeEmail") stripeEmail: String,
//            @Field("stripeToken") stripeToken: String
//    )

    @POST("checkout/pay")
    suspend fun pay(
            @Body token: String
    )
}