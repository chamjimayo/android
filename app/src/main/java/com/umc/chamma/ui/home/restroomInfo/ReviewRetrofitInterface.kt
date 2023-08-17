package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.*

interface ReviewRetrofitInterface {

    @GET("/api/review/list/high-rating/{restroomId}")
    fun tryToGetReviewListHR(@Path("restroomId") restroomId:Int) : Call<ReviewResponse>

    @GET("/api/review/list/latest/{restroomId}")
    fun tryToGetReviewListLatest(@Path("restroomId") restroomId:Int) : Call<ReviewResponse>


    @GET("/api/review/list/latest/{restroomId}")
    fun tryToGetReviewListLR(@Path("restroomId") restroomId:Int) : Call<ReviewResponse>
}
