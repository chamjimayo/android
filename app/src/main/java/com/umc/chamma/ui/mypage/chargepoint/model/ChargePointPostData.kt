package com.umc.chamma.ui.mypage.chargepoint.model

import com.google.gson.annotations.SerializedName

data class ChargePointPostData(
    @SerializedName("productId")val productId : String,
    @SerializedName("token")val token : String
)
