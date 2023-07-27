package com.android.chamma.util

import com.android.chamma.config.App.Companion.sharedPreferences

object Jwt {
    var jwt = ""
        private set

    var refreshToken = ""
        private set

    fun setjwt(token : String){
        jwt = token
        storejwt()
    }

    fun setRefreshToken(token : String){
        refreshToken = token
        storeRefreshToken()
    }

    private fun storeRefreshToken(){
        sharedPreferences.edit()
            .putString("refreshToken", refreshToken)
            .apply()
    }

    private fun storejwt(){
        sharedPreferences.edit()
            .putString("jwt",jwt)
            .apply()
    }
}