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
import com.umc.chamma.util.Constants.X_ACCESS_TOKEN
import com.umc.chamma.util.Constants.xapikey
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccessTokenInterceptor(private val context: Context) : Interceptor, RefreshTokenInterface {

    private lateinit var jwtToken : String

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
        autoLogin()

        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("Bearer-Token", jwtToken)
        builder.addHeader("x-api-key", xapikey)
        return chain.proceed(builder.build())
    }


    private fun autoLogin(){
        val jwt = sharedPreferences.getString(X_ACCESS_TOKEN,"")?:""
        val refreshToken = sharedPreferences.getString(Constants.X_REFRESH_TOKEN,"")?:""
        val accessExpire = sharedPreferences.getString(Constants.X_ACCESS_EXPIRE,"")?:""
        val refreshExpire = sharedPreferences.getString(Constants.X_REFRESH_EXPIRE,"")?:""


        if (jwt.isNotBlank()) {
            if(isDatePassed(accessExpire)){
                if(isDatePassed(refreshExpire)){
                    // TODO 세션만료 모달
                }else RefreshTokenService(this).refreshJwt(RefreshJwtPostData(refreshToken))

            }else jwtToken = jwt
        }else{
            // TODO 세션만료 모달
        }
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
        jwtToken = data.accessToken
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