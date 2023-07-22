package com.android.chamma.util

import com.android.chamma.App
import com.android.chamma.App.Companion.sharedPreferences

object Jwt {
    var jwt = ""
        private set

    fun setjwt(token : String){
        jwt = token
        storejwt()
    }

    private fun storejwt(){
        sharedPreferences.edit()
            .putString("jwt",jwt)
            .apply()
    }
}