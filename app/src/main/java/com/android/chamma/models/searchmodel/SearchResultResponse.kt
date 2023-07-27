package com.android.chamma.models.searchmodel

import com.google.gson.annotations.SerializedName

data class SearchResultResponse (
    val code : String,
    val msg : String,
    val data : ArrayList<SearchResultData>
)

data class SearchResultData(
    @SerializedName("searchWord") val searchWord : String,
    @SerializedName("roadAddress") val roadAddress : String,
    @SerializedName("lotNumberAddress ") val lotNumberAddress : String,
    @SerializedName("name") val name : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)