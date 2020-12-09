package com.example.eshop.api

import android.content.Context
import com.example.eshop.services.CartService
import com.example.eshop.services.LoginService
import com.example.eshop.services.PaymentService
import com.example.eshop.services.ProductService
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor(private val context: Context) {

    private val okHttpClient: OkHttpClient by lazy {
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
        OkHttpClient().newBuilder()
            .cookieJar(cookieJar)
            .addInterceptor(ErrorInterceptor(cookieJar))
            .build()
    }

    private val builder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val loginService: LoginService by lazy {
        builder.build()
            .create(LoginService::class.java)
    }

    val productService: ProductService by lazy {
        builder.build()
            .create(ProductService::class.java)
    }

    val cartService: CartService by lazy {
        builder.build()
            .create(CartService::class.java)
    }

    val paymentService: PaymentService by lazy {
        builder.build()
            .create(PaymentService::class.java)
    }

    companion object {
        const val BASE_URL = "https://e-shopdotnet.herokuapp.com/api/"

        @Volatile
        private var instance: NetworkService? = null

        fun getInstance(context: Context): NetworkService {
            return instance ?: synchronized(this) {
                instance ?: NetworkService(context).also { instance = it }
            }
        }
    }
}

class ErrorInterceptor(private val cookieJar: ClearableCookieJar) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        when (response.code()) {
            401, 403 -> {
                cookieJar.clear()
                // TODO: goto login page
            }
        }

        return response
    }
}