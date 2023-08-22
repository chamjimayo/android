package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.config.App
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.model.MypageReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageReviewService(val view: MypageReviewInterface) {

    fun getUserReviewInfo() {

        val userReviewInfoRetro = App.getRetro().create(mypageReviewAPI::class.java)
        userReviewInfoRetro.getUserReviewInfo()
            .enqueue(object : Callback<MypageReviewResponse> {
                override fun onResponse(
                    call: Call<MypageReviewResponse>,
                    response: Response<MypageReviewResponse>
                ) {
                    response.body()?.let {
                        if (response.code() == 200) view.onGetUserReviewInfoSuccess(response.body()!!.data)
                        else view.onGetUserReviewInfoFail("유저정보 불러오기 실패")
                    }
                    if (response.body() == null) view.onGetUserReviewInfoFail("유저정보 불러오기 실패")
                }

                override fun onFailure(call: Call<MypageReviewResponse>, t: Throwable) {
                    view.onGetUserReviewInfoFail(t.message.toString())
                }
            })
    }
}