package com.example.eshop.services

import com.example.eshop.models.Credentials
import com.example.eshop.models.User
import retrofit2.http.*

interface LoginService {
    @POST("accounts/login")
    suspend fun login(
        @Body credentials : Credentials
    )

    @POST("accounts/logout")
    suspend fun logout()

    @GET("accounts/me")
    suspend fun getMe(): User
}