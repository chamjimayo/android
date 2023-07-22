package com.android.chamma.models.homemodel

data class LocationResponse (
    val code : String,
    val msg : String,
    val data : ArrayList<MarkerData>
)

data class MarkerData(
    val restroomName : String,
    val longitude : Double,
    val latitude : Double,
    val rating : Float,
    val distance : Double,
    val publicOrPaid : String
)