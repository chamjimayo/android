package com.umc.chamma.ui.login

import android.util.Log
import com.umc.chamma.config.App
import com.umc.chamma.ui.login.model.LoginPostData
import com.umc.chamma.ui.login.model.LoginResponse
import com.umc.chamma.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view : LoginActivityInterface) {


    fun postLogin(body : LoginPostData){
        val loginRetro = App.getRetro().create(LoginRetrofitInterface::class.java)
        loginRetro.postLogin(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.body()?.let{
                    if(response.code() == 200) view.onPostLoginSuccess(it.data)
                    else view.onPostLoginFailure("최초가입 사용자",body.authId)
                }
                if(response.body()==null){
                    view.onPostLoginFailure("최초가입 사용자",body.authId)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onPostLoginFailure("네트워크 오류", "")
            }
        })

    }


}