package com.umc.chamma.ui.reviewwrite

import com.umc.chamma.config.App
import com.umc.chamma.ui.reviewwrite.model.ReviewWritePostData
import com.umc.chamma.ui.reviewwrite.model.ReviewWriteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewWriteService(val view : ReviewWriteActivityInterface) {



    fun postReviewWrite(body : ReviewWritePostData){
        val reviewRetro = App.getRetro().create(ReviewWriteRetrofitInterface::class.java)
        reviewRetro.postReviewWrite(body).enqueue(object : Callback<ReviewWriteResponse>{
            override fun onResponse(
                call: Call<ReviewWriteResponse>,
                response: Response<ReviewWriteResponse>
            ) {
                if(response.body() != null){
                    if(response.code() == 200) view.onPostReviewWriteSuccess(response.body()!!.data)
                    else view.onPostReviewWriteFailure("API 통신 실패")
                }else view.onPostReviewWriteFailure("API 통신 실패")
            }

            override fun onFailure(call: Call<ReviewWriteResponse>, t: Throwable) {
                view.onPostReviewWriteFailure(t.message.toString())
            }
        })
    }
}