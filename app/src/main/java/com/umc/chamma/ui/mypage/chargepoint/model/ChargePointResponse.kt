package com.umc.chamma.ui.mypage.chargepoint.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class ChargePointResponse(
    @SerializedName("data")val data : ChargePointResult
) : BaseResponse()

data class ChargePointResult(
    @SerializedName("point") val point : Int
)
