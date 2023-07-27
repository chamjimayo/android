package com.android.chamma.ui.search.network

import com.android.chamma.models.searchmodel.SearchResultResponse
import com.android.chamma.util.Constants.xapikey
import com.android.chamma.util.Jwt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchAPI {
    @GET("/api/address/search")
    fun getSearch(
        @Query("searchWord") searchWord : String,
        @Header("x-api-key") api : String? = xapikey,
        @Header("Bearer-Token") jwt : String? = "Bearer " + Jwt.jwt
    ) : Call<SearchResultResponse>
}