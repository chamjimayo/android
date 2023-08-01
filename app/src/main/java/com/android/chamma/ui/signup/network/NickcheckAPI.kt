package com.android.chamma.ui.signup.network

import com.android.chamma.ui.signup.model.NickcheckResponse
import com.android.chamma.util.Constants.xapikey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NickcheckAPI {
    @GET("/api/users/check-nickname/{nickname}")
    fun checkNick(
        @Path("nickname") nickname : String,
        @Header("x-api-key") api : String?=xapikey
    ) : Call<NickcheckResponse>
}