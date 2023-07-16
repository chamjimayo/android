package com.android.chamma.ui.signup.network

import com.android.chamma.models.signupmodel.SignupPostData
import com.android.chamma.models.signupmodel.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI {
    @POST("/api/auth/signup")
    fun signup(
        @Body params : SignupPostData
    ): Call<SignupResponse>
}