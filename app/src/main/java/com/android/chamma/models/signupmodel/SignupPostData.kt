package com.android.chamma.models.signupmodel

data class SignupPostData(
    val authType : String,
    val authId : String,
    val name : String,
    val nickname : String,
    val gender : String
)
