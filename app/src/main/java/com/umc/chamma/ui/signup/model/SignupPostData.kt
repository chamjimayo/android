package com.umc.chamma.ui.signup.model

data class SignupPostData(
    val authType : String,
    val authId : String,
    val name : String,
    val nickname : String,
    val gender : String,
    val userProfile : String
)
