package com.umc.chamma.ui.mypage.usage.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MypageUsageAPI {

    @GET("/api/users/me/used-restrooms")
    fun getUserUsageInfo(
        @Query("page") page : Int,
        @Query("size") size : Int
    ): Call<>
}