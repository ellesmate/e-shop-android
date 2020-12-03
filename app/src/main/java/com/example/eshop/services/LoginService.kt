package com.example.eshop.services

import retrofit2.http.Field
import retrofit2.http.POST

interface LoginService {
    @POST("accounts/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    )

    @POST("accounts/logout")
    suspend fun logout()
}