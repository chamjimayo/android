package com.umc.chamma.ui.home.restroomInfo.model


import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class ReviewResponse(
    @SerializedName("data")
    val `data`: List<ReviewData>,
):BaseResponse()
data class ReviewData(
    @SerializedName("dateTime")
    val dateTime: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("restroomId")
    val restroomId: Int,
    @SerializedName("reviewContent")
    val reviewContent: String,
    @SerializedName("reviewId")
    val reviewId: Int,
    @SerializedName("userId")
    val userId: Int
)