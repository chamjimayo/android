package com.android.chamma.ui.search.model

import com.android.chamma.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SearchResultResponse (
    val data : ArrayList<SearchResultData>
) : BaseResponse()

data class SearchResultData(
    @SerializedName("searchWord") val searchWord : String,
    @SerializedName("roadAddress") val roadAddress : String,
    @SerializedName("lotNumberAddress") val lotNumberAddress : String,
    @SerializedName("name") val name : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)