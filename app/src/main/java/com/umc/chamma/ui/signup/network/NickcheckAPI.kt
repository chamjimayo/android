package com.umc.chamma.ui.signup.network

import com.umc.chamma.ui.signup.model.NickcheckResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NickcheckAPI {
    @GET("/api/users/check-nickname/{nickname}")
    fun checkNick(
        @Path("nickname") nickname : String
    ) : Call<NickcheckResponse>
}