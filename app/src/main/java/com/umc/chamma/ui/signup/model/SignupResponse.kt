package com.umc.chamma.ui.signup.model

import com.umc.chamma.config.BaseResponse

data class SignupResponse(
    val data : SignupResponseData
): BaseResponse()

data class SignupResponseData(
    val accessToken : String,
    val refreshToken : String,
    val accessTokenExpiredDate : String,
    val refreshTokenExpiredDate : String
)
