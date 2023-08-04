package com.umc.chamma.ui.login

import com.umc.chamma.ui.login.model.LoginPostData
import com.umc.chamma.ui.login.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {


    @POST("/api/auth/login")
    fun postLogin(@Body params : LoginPostData) : Call<LoginResponse>
}