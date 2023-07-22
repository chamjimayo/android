package com.android.chamma.ui.login.network

import com.android.chamma.models.loginmodel.LoginPostData
import com.android.chamma.models.loginmodel.LoginResponse
import com.android.chamma.util.Constants.xapikey
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginAPI {
    @POST("/api/auth/login")
    fun checkUuid(
        @Body params : LoginPostData,
        @Header("x-api-key") api : String? = xapikey
    ) : Call<LoginResponse>
}