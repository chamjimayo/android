package com.umc.chamma.ui.using.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class EnduseResponse(
    @SerializedName("data")val data : EndUseResponseData
):BaseResponse()


data class EndUseResponseData(
    @SerializedName("userId")val userId : Int,
    @SerializedName("restroomId")val restroomId : Int
)
