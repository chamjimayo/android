package com.android.chamma.models.homemodel

import com.android.chamma.config.BaseResponse

data class NearToiletResponse (
    val data : ArrayList<MarkerData>
) : BaseResponse()

data class MarkerData(
    val restroomName : String,
    val longitude : Double,
    val latitude : Double,
    val address : String,
    val publicOrPaid : String,
    val reviewRating : Float,
    val distance : Double,
)