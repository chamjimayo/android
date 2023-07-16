package com.android.chamma.models.signupmodel

import com.android.chamma.models.loginmodel.LoginResponseData

data class SignupResponse(
    val code : Int,
    val msg : String,
    val data : SignupResponseData
)

data class SignupResponseData(
    val accessToken : String,
    val refreshToken : String
)
