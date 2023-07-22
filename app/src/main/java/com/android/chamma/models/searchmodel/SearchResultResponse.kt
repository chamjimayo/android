package com.android.chamma.models.searchmodel

data class SearchResultResponse (
    val code : String,
    val msg : String,
    val data : ArrayList<SearchResultData>
)

data class SearchResultData(
    val roadAddress : String,
    val lotNumberAddress : String,
    val name : String
)