package com.android.chamma.ui.search

import com.android.chamma.config.BaseResponse
import com.android.chamma.ui.search.model.SearchResultData
import com.android.chamma.ui.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchRetrofitInterface {

    @GET("/api/address/search")
    fun getSearch(@Query("searchWord") searchWord : String ) : Call<SearchResultResponse>

    @GET("/api/address/search/recent")
    fun getRecentKeyword() : Call<SearchResultResponse>

    @POST("/api/address/search/click")
    fun postAddressClick(@Body params : SearchResultData) : Call<BaseResponse>
}