package com.umc.chamma.ui.login

import com.umc.chamma.ui.login.model.LoginResponseData

interface LoginActivityInterface {

    fun onPostLoginSuccess(result : LoginResponseData)

    fun onPostLoginFailure(message : String, uuid : String)

}