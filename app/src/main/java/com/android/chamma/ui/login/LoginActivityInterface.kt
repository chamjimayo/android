package com.android.chamma.ui.login

import com.android.chamma.models.loginmodel.LoginResponseData

interface LoginActivityInterface {


    fun onPostLoginSuccess(result : LoginResponseData)

    fun onPostLoginFailure(message : String, uuid : String)
}