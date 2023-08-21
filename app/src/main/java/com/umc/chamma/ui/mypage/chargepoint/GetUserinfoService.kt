package com.umc.chamma.ui.mypage.chargepoint

import com.umc.chamma.config.App
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetUserinfoService(val view : GetUserinfoInterface){


    fun getUserInfo(){

        val userInfoRetro = App.getRetro().create(ChargePointRetrofitInterface::class.java)
        userInfoRetro.getUserInfo()
            .enqueue(object : Callback<UserinfoResponse>{
                override fun onResponse(
                    call: Call<UserinfoResponse>,
                    response: Response<UserinfoResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) view.onGetUserInfoSuccess(response.body()!!.data)
                        else view.onGetUserInfoFailure("유저정보 불러오기 실패")
                    }
                    if(response.body() == null) view.onGetUserInfoFailure("유저정보 불러오기 실패")
                }

                override fun onFailure(call: Call<UserinfoResponse>, t: Throwable) {
                    view.onGetUserInfoFailure(t.message.toString())
                }
            })
    }

}