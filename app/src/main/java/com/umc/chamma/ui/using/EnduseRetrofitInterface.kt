package com.umc.chamma.ui.using

import com.umc.chamma.ui.using.model.EnduseResponse
import retrofit2.Call
import retrofit2.http.POST

interface EnduseRetrofitInterface {
    @POST("/api/restroom/endofuse")
    fun postEnduse(
    ): Call<EnduseResponse>
}