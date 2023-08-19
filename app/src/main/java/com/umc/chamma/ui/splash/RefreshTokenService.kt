package com.umc.chamma.ui.splash

import com.umc.chamma.config.App
import com.umc.chamma.ui.login.model.LoginResponse
import com.umc.chamma.ui.splash.model.RefreshJwtPostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RefreshTokenService(val view : RefreshTokenInterface) {

    fun refreshJwt(body : RefreshJwtPostData){
        val refreshRetro = App.getRetro().create(RefreshTokenRetrofitInterface::class.java)
        refreshRetro.refreshJwt(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.body()==null) view.onPostRefreshJwtFailure("accessToken 갱신 실패")
                else{
                    if(response.code() == 200) view.onPostRefreshJwtSuccess(response.body()!!.data)
                    else view.onPostRefreshJwtFailure("accessToken 갱신 실패")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onPostRefreshJwtFailure(t.message.toString())
            }
        })

    }

}