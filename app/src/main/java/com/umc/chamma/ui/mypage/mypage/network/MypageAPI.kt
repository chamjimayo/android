package com.umc.chamma.ui.mypage.mypage.network


import com.umc.chamma.ui.signup.model.NickcheckResponse
import retrofit2.Call
import retrofit2.http.GET

interface MypageAPI {

    @GET("/api/users/me")
    fun checkUSerData(): Call<NickcheckResponse>
}