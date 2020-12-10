package com.example.eshop.services

import com.example.eshop.models.Product
import com.example.eshop.models.ProductDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
//    @GET("products/{skip}/{take}")
//    suspend fun getProducts(
//        @Path("skip") skip: Int,
//        @Path("take") take: Int
//    ): List<Product>

    @GET("products")
    suspend fun getProductsByCategory(
            @Query("category") category: String
    ): List<Product>

    @GET("products")
    suspend fun getProducts(
    ): List<Product>

    @GET("products/{slug}")
    suspend fun getProduct(
        @Path("slug") slug: String
    ): ProductDetail
}