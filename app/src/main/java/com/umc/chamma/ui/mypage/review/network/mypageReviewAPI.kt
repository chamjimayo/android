package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.model.MypageReviewResponse
import retrofit2.Call
import retrofit2.http.GET

interface mypageReviewAPI {

    @GET("/api/review/list")
    fun getUserReviewInfo () : Call<MypageReviewResponse>
}