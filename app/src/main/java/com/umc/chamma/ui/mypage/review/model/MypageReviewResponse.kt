package com.umc.chamma.ui.mypage.review.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class MypageReviewResponse(
    @SerializedName("data") val data: ArrayList<MypageReviewData>
):BaseResponse()

data class MypageReviewData(

    @SerializedName("reviewId")
    val reviewId : Int,

    @SerializedName("userId")
    val userId : Int,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("userProfile")
    val userprofile : String,

    @SerializedName("restoomId")
    val restroomId : Int,

    @SerializedName("reviewContent")
    val reviewContent : String,

    @SerializedName("rating")
    val rating : Float,

    @SerializedName("dateTime")
    val dateTime: String
)

