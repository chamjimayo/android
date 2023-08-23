package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.model.MypageReviewResponse
import com.umc.chamma.ui.mypage.review.model.PatchreviewPostData
import retrofit2.Call
import retrofit2.http.*

interface mypageReviewAPI {

    @GET("/api/review/list")
    fun getUserReviewInfo () : Call<MypageReviewResponse>

    @DELETE("/api/review/{reviewId}")
    fun deleteUserReview(
        @Path("reviewId") reviewId : Int
    ) : Call<BaseResponse>


    @PATCH("/api/review/{reviewId}")
    fun patchUserReview(
        @Path("ReviewId") reviewId : Int,
        @Body params : PatchreviewPostData
    ) : Call<BaseResponse>
}