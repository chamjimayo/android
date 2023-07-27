package com.android.chamma.ui.login

import com.android.chamma.ui.login.model.LoginPostData
import com.android.chamma.ui.login.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {


    @POST("/api/auth/login")
    fun postLogin(@Body params : LoginPostData) : Call<LoginResponse>
}