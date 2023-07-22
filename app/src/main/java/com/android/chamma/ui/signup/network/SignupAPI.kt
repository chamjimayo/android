package com.android.chamma.ui.signup.network

import com.android.chamma.models.signupmodel.SignupPostData
import com.android.chamma.models.signupmodel.SignupResponse
import com.android.chamma.util.Constants
import com.android.chamma.util.Constants.xapikey
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignupAPI {
    @POST("/api/auth/signup")
    fun signup(
        @Body params : SignupPostData,
        @Header("x-api-key") api : String?= xapikey
    ): Call<SignupResponse>
}