package com.umc.chamma.ui.login.model

import com.umc.chamma.config.BaseResponse

data class LoginResponse(
    val data : LoginResponseData
) : BaseResponse()

data class LoginResponseData(
    val accessToken : String,
    val refreshToken : String,
    val accessTokenExpiredDate : String,
    val refreshTokenExpiredDate : String

)
