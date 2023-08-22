package com.umc.chamma.ui.mypage.chargepoint.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class UserinfoResponse(
    @SerializedName("data")val data : UserinfoData
):BaseResponse()

data class UserinfoData(
    @SerializedName("name")val name : String,
    @SerializedName("nickname")val nickname : String,
    @SerializedName("point")val point : Int,
    @SerializedName("gender")val gender : String,
    @SerializedName("userProfile")val userProfile : String,
    @SerializedName("restroomUsing")val restroomUsing : Boolean
)
