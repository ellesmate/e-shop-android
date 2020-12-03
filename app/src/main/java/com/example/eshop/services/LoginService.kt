package com.example.eshop.services

import com.example.eshop.models.Credentials
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface LoginService {
    @POST("accounts/login")
    suspend fun login(
        @Body credentials : Credentials
    )

    @POST("accounts/logout")
    suspend fun logout()
}