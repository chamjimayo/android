package com.umc.chamma.ui.mypage.network

import androidx.browser.trusted.Token
import com.kakao.sdk.user.model.AccessTokenInfo
import com.umc.chamma.ui.mypage.mypage.network.MypageResponse
import com.umc.chamma.util.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import kotlin.jvm.Throws

interface MypageAPI {

    @GET("/api/users/me")
    fun checkUSerData(
//        @Header("authorization") accessToken : String
    ): Call<MypageResponse>

}