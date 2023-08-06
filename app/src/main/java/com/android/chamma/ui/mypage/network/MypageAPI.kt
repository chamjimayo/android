package com.android.chamma.ui.mypage.network

import com.android.chamma.models.signupmodel.NickcheckResponse
import retrofit2.Call
import retrofit2.http.GET

interface MypageAPI {

    @GET("/api/users/me")
    fun checkUSerData(): Call<NickcheckResponse>
}