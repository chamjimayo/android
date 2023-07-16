package com.android.chamma.models.signupmodel

data class NickcheckResponse(
    val code : Int,
    val msg : String,
    val data : NickcheckData
)

data class NickcheckData(
    val nicknameDuplicate : Boolean
)