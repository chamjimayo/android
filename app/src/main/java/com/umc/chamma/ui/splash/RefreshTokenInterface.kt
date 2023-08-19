package com.umc.chamma.ui.splash

import com.umc.chamma.ui.login.model.LoginResponseData

interface RefreshTokenInterface {

    fun onPostRefreshJwtSuccess(data : LoginResponseData)

    fun onPostRefreshJwtFailure(message : String)
}