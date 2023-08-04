package com.umc.chamma.ui.search.model

import com.umc.chamma.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SearchResultResponse (
    val data : ArrayList<SearchResultData>
) : com.umc.chamma.config.BaseResponse()

data class SearchResultData(
    @SerializedName("searchWord") val searchWord : String,
    @SerializedName("roadAddress") val roadAddress : String,
    @SerializedName("lotNumberAddress") val lotNumberAddress : String,
    @SerializedName("name") val name : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)