package com.android.chamma.models.loginmodel

data class LoginResponse(
    val code : Int,
    val msg : String,
    val data : LoginResponseData
)

data class LoginResponseData(
    val accessToken : String,
    val refreshToken : String
)
