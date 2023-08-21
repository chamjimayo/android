package com.umc.chamma.ui.qr

import com.google.gson.annotations.SerializedName

data class DeductPointRequest(
    @SerializedName("point")
    val point: Int,
)