package com.umc.chamma.ui.qr

import com.google.gson.annotations.SerializedName

data class UseRestroomRequest(
    @SerializedName("restroomId")
    val restroomId: Int,
)