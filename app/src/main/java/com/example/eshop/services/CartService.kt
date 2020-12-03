package com.example.eshop.services

import com.example.eshop.models.CartItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartService {
    @POST("cart/add/{stockId}/{qty}")
    suspend fun addToCart(
        @Path("stockId") stockId: Int,
        @Path("qty") qty: Int
    )

    @POST("cart/remove/{stockId}/{qty}")
    suspend fun removeFromCart(
        @Path("stockId") stockId: Int,
        @Path("qty") qty: Int
    )

    @GET("cart")
    suspend fun getCart(): List<CartItem>
}