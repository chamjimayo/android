package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.config.App
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse
import com.umc.chamma.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService(val view : ReviewActivityInterface) {

//val accessToken="Bearer e1323423534"

    fun tryToGetReviewListLatest(restroomId:Int){
        val ReviewRetrofitInterface = App.getRetro().create(ReviewRetrofitInterface::class.java)
        ReviewRetrofitInterface.tryToGetReviewListLatest(restroomId = restroomId)
            .enqueue(object : Callback<ReviewResponse>{
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onTryToGetRL_LatestSuccess(it)
                    else view.onTryToGetRL_LatestFailure("API 오류")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                view.onTryToGetRL_LatestFailure("네트워크 오류 ${t.toString()}")
            }
        })
    }


    fun tryToGetReviewListHR(restroomId:Int){

        val ReviewRetrofitInterface = App.getRetro().create(ReviewRetrofitInterface::class.java)
        ReviewRetrofitInterface.tryToGetReviewListHR(restroomId = restroomId)
            .enqueue(object : Callback<ReviewResponse>{
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) view.onTryToGetRL_HRSuccess(it)
                        else view.onTryToGetRL_HRFailure("API 오류")
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.onTryToGetRL_HRFailure("네트워크 오류 ${t.toString()}")
                }
            })
    }

    fun tryToGetReviewListLR(restroomId:Int){

        val ReviewRetrofitInterface = App.getRetro().create(ReviewRetrofitInterface::class.java)
        ReviewRetrofitInterface.tryToGetReviewListLR(restroomId = restroomId)
            .enqueue(object : Callback<ReviewResponse>{
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) view.onTryToGetRL_LRSuccess(it)
                        else view.onTryToGetRL_LRFailure("API 오류")
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.onTryToGetRL_LRFailure("네트워크 오류 ${t.toString()}")
                }
            })
    }
}