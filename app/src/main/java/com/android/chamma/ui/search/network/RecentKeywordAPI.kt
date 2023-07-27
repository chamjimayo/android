package com.android.chamma.ui.search.network


import com.android.chamma.models.searchmodel.SearchResultResponse
import com.android.chamma.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RecentKeywordAPI {
    @GET("/api/address/search/recent")
    fun getRecentKeyword() : Call<SearchResultResponse>
}