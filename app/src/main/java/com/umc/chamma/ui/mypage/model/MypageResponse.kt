package com.umc.chamma.ui.mypage.mypage.network

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class MypageResponse(
    val data: MypageResponseData
): BaseResponse()

data class MypageResponseData(
    @SerializedName("name")
    val name: String,

    @SerializedName("nickname")
    val nickName: String,

    @SerializedName("point")
    val point : Int
)