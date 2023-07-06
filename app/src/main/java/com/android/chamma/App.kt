package com.android.chamma

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.Constants.kakaoAppKey
import com.android.chamma.util.Constants.naverClientId
import com.android.chamma.util.Constants.naverClientName
import com.android.chamma.util.Constants.naverClientSecret
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK

class App : Application() {

    //  앱의 context 를 instance 변수에 저장
    init{
        instance =this
    }

    companion object{
        private var instance : App? = null
        lateinit var sharedPreferences: SharedPreferences

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("SP", MODE_PRIVATE)

        getkakaoKeyhash()
        startSocialLogin()
    }

    private fun getkakaoKeyhash() {
        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
    }

    private fun startSocialLogin() {
        KakaoSdk.init(this, kakaoAppKey)
        NaverIdLoginSDK.initialize(this,
            naverClientId,
            naverClientSecret,
            naverClientName
        )
    }

}