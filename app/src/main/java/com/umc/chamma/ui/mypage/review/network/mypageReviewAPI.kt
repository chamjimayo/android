package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import retrofit2.Call
import retrofit2.http.GET

interface mypageReviewAPI {

    @GET("/api/review/list")
    fun checkReview () : Call<MypageReviewData>
}