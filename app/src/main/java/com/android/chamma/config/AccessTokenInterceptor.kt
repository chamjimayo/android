package com.android.chamma.config

import android.content.Context
import com.android.chamma.config.App.Companion.X_ACCESS_TOKEN
import com.android.chamma.config.App.Companion.sharedPreferences
import com.android.chamma.util.Constants.xapikey
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("Bearer-Token", jwtToken)
        }
        builder.addHeader("x-api-key", xapikey)
        return chain.proceed(builder.build())
    }
}