package com.android.chamma.config

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("code") val code : String = "",
    @SerializedName("msg") val msg : String = ""
)
