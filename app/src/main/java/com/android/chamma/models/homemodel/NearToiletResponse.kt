package com.android.chamma.models.homemodel

data class NearToiletResponse (
    val code : String,
    val msg : String,
    val data : ArrayList<MarkerData>
)

data class MarkerData(
    val restroomName : String,
    val longitude : Double,
    val latitude : Double,
    val address : String,
    val publicOrPaid : String,
    val reviewRating : Float,
    val distance : Double,
)