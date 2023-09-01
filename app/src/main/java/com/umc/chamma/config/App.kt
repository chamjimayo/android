package com.umc.chamma.config

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.umc.chamma.util.Constants
import com.umc.chamma.util.Constants.TAG
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import com.umc.chamma.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {

    //  앱의 context 를 instance 변수에 저장
    init{
        instance =this
    }

    companion object{
        lateinit var instance : App
        lateinit var sharedPreferences: SharedPreferences
        lateinit var retrofit : Retrofit

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance.applicationContext
        }

        fun getRetro() : Retrofit{
            return retrofit.newBuilder()
                .baseUrl(Constants.BASE_URL)
                .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("CHAMMA_APP", MODE_PRIVATE)
        initRetrofitInstance()
        getkakaoKeyhash()
        startSocialLogin()
    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(AccessTokenInterceptor(applicationContext)) // JWT 자동 헤더 전송
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getkakaoKeyhash() {
        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
    }

    private fun startSocialLogin() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        NaverIdLoginSDK.initialize(this,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            BuildConfig.NAVER_CLIENT_NAME
        )
    }

}