package com.android.chamma

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {


    private val BASE_URL = "https://test.com"

    //  앱의 context 를 instance 변수에 저장
    init{
        instance =this

    }

    companion object{
        private var instance : App? = null
        lateinit var sharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("SP", MODE_PRIVATE)

        initRetrofitInstance()
    }

    private fun initRetrofitInstance() {

        // Logcat 에 통신내역 띄우기
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // Retrofit 객체
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}