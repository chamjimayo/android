package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.config.App
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.review.model.PatchreviewPostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewChangeService(val view : ReviewChangeInerface) {


    fun patchUserReview(reviewId : Int, data : PatchreviewPostData){
        val userReviewInfoRetro = App.getRetro().create(mypageReviewAPI::class.java)
        userReviewInfoRetro.patchUserReview(reviewId,data)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if(response.body() != null){
                        if(response.code() == 200) view.onPatchReviewSuccess()
                        else view.onPatchReviewFailure("API 통신 실패")
                    } else view.onPatchReviewFailure("API 통신 실패")
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPatchReviewFailure(t.message.toString())
                }
            })
    }
}