package com.android.chamma.ui.signup.model

import com.android.chamma.config.BaseResponse

data class SignupResponse(
    val data : SignupResponseData
): BaseResponse()

data class SignupResponseData(
    val accessToken : String,
    val refreshToken : String
)
