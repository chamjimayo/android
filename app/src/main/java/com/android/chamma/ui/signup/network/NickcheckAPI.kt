package com.android.chamma.ui.signup.network

import com.android.chamma.models.signupmodel.NickcheckResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NickcheckAPI {
    @GET("/api/users/check-nickname/{nickname}")
    fun checkNick(
        @Path("nickname") nickname : String
    ) : Call<NickcheckResponse>
}