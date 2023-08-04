package com.umc.chamma.ui.login.model

import com.umc.chamma.config.BaseResponse

data class LoginResponse(
    val data : LoginResponseData
) : com.umc.chamma.config.BaseResponse()

data class LoginResponseData(
    val accessToken : String,
    val refreshToken : String,
    val accessTokenValidityMs : Long,
    val refreshTokenValidityMs : Long

)
