package com.android.chamma.ui.search.network

import com.android.chamma.models.searchmodel.RecentKeywordResponse
import com.android.chamma.util.Constants
import com.android.chamma.util.Jwt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RecentKeywordAPI {
    @GET("/api/address/search/recent")
    fun getRecentKeyword(
        @Header("x-api-key") api : String? = Constants.xapikey,
        @Header("Bearer-Token") jwt : String? = Jwt.jwt
    ) : Call<RecentKeywordResponse>
}