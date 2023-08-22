package com.umc.chamma.ui.mypage.review.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class MypageReviewResponse(
    @SerializedName("data") val data: ArrayList<MypageReviewData>
):BaseResponse()

data class MypageReviewData(

    @SerializedName("reviewId")
    val reviewId : Int = 1,

    @SerializedName("userId")
    val userId : Int = 1,

    @SerializedName("nickname")
    val nickname : String = "nickname",

    @SerializedName("userProfile")
    val userprofile : String = "https://example.com/profile.jpg",

    @SerializedName("restoomId")
    val restroomId : Int = 1,

    @SerializedName("reviewContent")
    val reviewContent : String = "깔끔해요",

    @SerializedName("rating")
    val rating : Int = 4,

    @SerializedName("dateTime")
    val dateTime: String = "23.07.29")

