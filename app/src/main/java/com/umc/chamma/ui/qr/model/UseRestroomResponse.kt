package com.umc.chamma.ui.qr.model


import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class UseRestroomResponse(
    @SerializedName("data")
    val data: UseResponseData,

    ):BaseResponse()

data class UseRestroomResponseFailure(
    @SerializedName("data")
    val data: UseResponseFailureData,

    ):BaseResponse()

data class UseResponseData(
    @SerializedName("restroomId")
    val restroomId: Int,
    @SerializedName("userId")
    val userId: Int
)

data class UseResponseFailureData(
    @SerializedName("status")
    val status: String,
    @SerializedName("msg")
    val msg: String
)