package com.umc.chamma.ui.reviewwrite

import com.umc.chamma.ui.reviewwrite.model.ReviewWritePostData
import com.umc.chamma.ui.reviewwrite.model.ReviewWriteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewWriteRetrofitInterface {
    @POST("/api/review")
    fun postReviewWrite(
        @Body params : ReviewWritePostData
    ): Call<ReviewWriteResponse>
}