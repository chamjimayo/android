package com.android.chamma.ui.login

import com.android.chamma.config.App
import com.android.chamma.ui.login.model.LoginPostData
import com.android.chamma.ui.login.model.LoginResponse
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
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onPostLoginFailure("네트워크 오류", "")
            }
        })

    }


}