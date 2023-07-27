package com.android.chamma.config

import android.content.Context
import com.android.chamma.config.App.Companion.sharedPreferences
import com.android.chamma.util.Constants.X_ACCESS_TOKEN
import com.android.chamma.util.Constants.xapikey
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor(private val context: Context) : Interceptor {

/* TODO 통신시 ACCESS_TOKEN 유효기간 확인
    ACCESS_TOKEN 만료기간지났을 경우
    -> 안지났을 경우 : MainActivity 로 이동
    -> 지났을 경우 : REFRESH_TOKEN 유효기간 확인
        -> 안지났을 경우 : /api/auth/token/access 로 ACCESSTOKEN 갱신
        -> 지났을 경우 : 세션만료 모달창 -> LoginActivity 로 이동
 */

    // ACCESS_TOKEN 유효기간 무한이라고 가정하고 우선 생략
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