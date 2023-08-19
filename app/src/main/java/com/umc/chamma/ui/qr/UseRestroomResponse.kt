package com.umc.chamma.ui.qr


import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData

data class UseRestroomResponse(
    @SerializedName("data")
    val `data`: List<UseResponseData>,

):BaseResponse()

data class UseResponseData(
    @SerializedName("restroomId")
    val restroomId: Int,
    @SerializedName("userId")
    val userId: Int
)