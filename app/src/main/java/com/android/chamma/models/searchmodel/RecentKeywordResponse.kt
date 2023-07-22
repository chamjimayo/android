package com.android.chamma.models.searchmodel

data class RecentKeywordResponse(
    val code : String,
    val msg : String,
    val data : ArrayList<RecentKeywordData>
)

data class RecentKeywordData(
    val roadAddress : String,
    val lotNumberAddress : String,
    val name : String
)
