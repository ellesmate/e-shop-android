package com.example.eshop.services

import com.example.eshop.models.Product
import com.example.eshop.models.ProductDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products/{skip}/{take}")
    suspend fun getProducts(
        @Path("skip") skip: Int,
        @Path("take") take: Int
    ): List<Product>

    @GET("products/{slug}")
    suspend fun getProduct(
        @Path("slug") slug: String
    ): ProductDetail
}