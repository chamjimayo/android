package com.umc.chamma.ui.qr


import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData

data class DeductPointResponse(
    @SerializedName("data")
    val data: List<DeductResponseData>

):BaseResponse()

data class DeductResponseData(
    @SerializedName("point")
    val point: Int,
    @SerializedName("userId")
    val userId: Int
)