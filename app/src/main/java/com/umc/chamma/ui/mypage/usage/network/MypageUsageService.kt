package com.umc.chamma.ui.mypage.usage.network

import com.umc.chamma.config.App
import com.umc.chamma.ui.mypage.usage.model.MypageUsageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageUsageService (val view : MypageUsageInterface) {

    fun getUserUsageInfo() {

        val userUsageInfoRetro = App.getRetro().create(MypageUsageAPI::class.java)
        userUsageInfoRetro.getUserUsageInfo(1, 1)
            .enqueue(object : Callback<MypageUsageResponse> {
                override fun onResponse(
                    call: Call<MypageUsageResponse>,
                    response: Response<MypageUsageResponse>
                ) {
                    response.body()?.let {
                        if (response.code() == 200) view.onGetUserUsageInfoSuccess(response.body()!!.data.content)
                        else view.onGetUserUsageInfoFail("유저정보 불러오기 실패")
                    }
                    if (response.body() == null) view.onGetUserUsageInfoFail("유저정보 불러오기 실패")
                }

                override fun onFailure(call: Call<MypageUsageResponse>, t: Throwable) {
                    view.onGetUserUsageInfoFail(t.message.toString())
                }
            })


    }
}