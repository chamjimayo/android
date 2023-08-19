package com.umc.chamma.ui.qr

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface QrRetrofitInterface {

    @POST("/api/restroom/use")
    fun tryToUseRestroom(@Body restroomId:Int) : Call<UseRestroomResponse>
}
