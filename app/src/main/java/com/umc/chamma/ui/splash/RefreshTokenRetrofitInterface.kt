package com.umc.chamma.ui.splash

import com.umc.chamma.ui.login.model.LoginResponse
import com.umc.chamma.ui.splash.model.RefreshJwtPostData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenRetrofitInterface {

    @POST("/api/auth/token/access")
    fun refreshJwt(
        @Body params : RefreshJwtPostData
    ) : Call<LoginResponse>
}