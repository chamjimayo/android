package com.android.chamma.ui.search

import com.android.chamma.config.BaseResponse
import com.android.chamma.ui.search.model.SearchResultData
import com.android.chamma.ui.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    @GET("/api/address/search")
    fun getSearch(@Query("searchWord") searchWord : String ) : Call<SearchResultResponse>

    @GET("/api/address/search/recent")
    fun getRecentKeyword() : Call<SearchResultResponse>

    @POST("/api/address/search/click")
    fun postAddressClick(@Body params : SearchResultData) : Call<BaseResponse>

    @DELETE("/api/address/search/{keyword}")
    fun deleteRecentKeyword(@Path("keyword") keyword : String) : Call<BaseResponse>

    @DELETE("/api/address/search/all")
    fun deleteAllRecentKeyword() : Call<BaseResponse>

}