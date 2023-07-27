package com.android.chamma.ui.search

import com.android.chamma.models.searchmodel.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {

    @GET("/api/address/search")
    fun getSearch(@Query("searchWord") searchWord : String ) : Call<SearchResultResponse>

    @GET("/api/address/search/recent")
    fun getRecentKeyword() : Call<SearchResultResponse>
}