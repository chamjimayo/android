package com.android.chamma.ui.login.model

import com.android.chamma.config.BaseResponse

data class LoginResponse(
    val data : LoginResponseData
) : BaseResponse()

data class LoginResponseData(
    val accessToken : String,
    val refreshToken : String,
    val accessTokenValidityMs : Long,
    val refreshTokenValidityMs : Long

)
