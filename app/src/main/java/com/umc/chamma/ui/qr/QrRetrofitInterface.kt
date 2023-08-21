package com.umc.chamma.ui.qr

import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoResponse
import com.umc.chamma.ui.qr.model.DeductPointRequest
import com.umc.chamma.ui.qr.model.DeductPointResponse
import com.umc.chamma.ui.qr.model.UseRestroomRequest
import com.umc.chamma.ui.qr.model.UseRestroomResponse
import retrofit2.Call
import retrofit2.http.*

interface QrRetrofitInterface {

    @POST("/api/restroom/use")
    fun tryToUseRestroom(@Body restroomId: UseRestroomRequest) : Call<UseRestroomResponse>

    @POST("/api/users/point/deduct")
    fun tryToDeductPoint(@Body point: DeductPointRequest) : Call<DeductPointResponse>

    @GET("/api/users/me")
    fun getUserInfo() : Call<UserinfoResponse>
}
