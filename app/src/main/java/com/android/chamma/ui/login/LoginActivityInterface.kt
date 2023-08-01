package com.android.chamma.ui.login

import com.android.chamma.ui.login.model.LoginResponseData

interface LoginActivityInterface {

    fun onPostLoginSuccess(result : LoginResponseData)

    fun onPostLoginFailure(message : String, uuid : String)

}