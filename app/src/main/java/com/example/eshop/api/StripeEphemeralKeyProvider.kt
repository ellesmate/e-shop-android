package com.example.eshop.api

import android.content.Context
import androidx.annotation.Size
import com.example.eshop.services.PaymentService
import com.google.gson.Gson
import com.stripe.android.EphemeralKeyProvider
import com.stripe.android.EphemeralKeyUpdateListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class StripeEphemeralKeyProvider(private val context: Context) : EphemeralKeyProvider {
    private val paymentService: PaymentService = NetworkService.getInstance(context).paymentService

    override fun createEphemeralKey(
            @Size(min = 4) apiVersion: String,
            keyUpdateListener: EphemeralKeyUpdateListener
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            val responseBody = paymentService.createEphemeralKey(apiVersion)
            withContext(Dispatchers.Main) {
                try {
                    val ephemeralKeyJson = Gson().toJson(responseBody)
                    keyUpdateListener.onKeyUpdate(ephemeralKeyJson)
                } catch (e: IOException) {
                    keyUpdateListener
                            .onKeyUpdateFailure(0, e.message ?: "")
                }
            }
        }
    }
}