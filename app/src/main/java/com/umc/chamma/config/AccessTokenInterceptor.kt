package com.umc.chamma.config

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.ui.login.LoginActivity
import com.umc.chamma.ui.login.model.LoginResponseData
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.splash.RefreshTokenInterface
import com.umc.chamma.ui.splash.RefreshTokenService
import com.umc.chamma.ui.splash.model.RefreshJwtPostData
import com.umc.chamma.util.Constants
import com.umc.chamma.util.Constants.SESSION_EXPIRED_ACTION
import com.umc.chamma.util.Constants.X_ACCESS_TOKEN
import com.umc.chamma.util.Constants.xapikey
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccessTokenInterceptor(private val context: Context) : Interceptor, RefreshTokenInterface {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = sharedPreferences.getString(X_ACCESS_TOKEN,"")?:""
        val refreshToken = sharedPreferences.getString(Constants.X_REFRESH_TOKEN,"")?:""
        val accessExpire = sharedPreferences.getString(Constants.X_ACCESS_EXPIRE,"")?:""
        val refreshExpire = sharedPreferences.getString(Constants.X_REFRESH_EXPIRE,"")?:""


        if (jwt.isNotBlank()) {
            if(isDatePassed(accessExpire)){
                if(isDatePassed(refreshExpire)){
                    startLoginActivity()
                }else RefreshTokenService(this).refreshJwt(RefreshJwtPostData(refreshToken))

            }else return initHeader(chain)
        }else{
            startLoginActivity()
        }

        return initHeader(chain)
    }

    private fun initHeader(chain : Interceptor.Chain): Response{
        val jwt: String? = sharedPreferences.getString(X_ACCESS_TOKEN, null)
        val builder: Request.Builder = chain.request().newBuilder()
        if(jwt != null){
            builder.addHeader("Bearer-Token", jwt)
            builder.addHeader("x-api-key", xapikey)
        }
        return chain.proceed(builder.build())
    }


    private fun startLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }


    // 현재날짜가 주어진날짜 보다 지났는지 판별해주는 함수
    private fun isDatePassed(dateStr : String) : Boolean{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val date = LocalDateTime.parse(dateStr, formatter)
        val now = LocalDateTime.now()

        return now.isAfter(date)
    }

    override fun onPostRefreshJwtSuccess(data: LoginResponseData) {
        storeTokens(data)
    }

    override fun onPostRefreshJwtFailure(message: String) {
    }

    private fun storeTokens(result : LoginResponseData){
        sharedPreferences.edit()
            .putString(X_ACCESS_TOKEN, "Bearer " + result.accessToken)
            .putString(Constants.X_REFRESH_TOKEN, result.refreshToken)
            .putString(Constants.X_ACCESS_EXPIRE, result.accessTokenExpiredDate)
            .putString(Constants.X_REFRESH_EXPIRE, result.refreshTokenExpiredDate)
            .apply()
    }

}