package com.umc.chamma.ui.mypage.chargepoint

import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointPostData
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointResponse
import com.umc.chamma.ui.mypage.chargepoint.model.ForceChargePointPostData
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChargePointRetrofitInterface {


    @GET("/api/users/me")
    fun getUserInfo() : Call<UserinfoResponse>

    @POST("/api/in-app/purchase/verify")
    fun postChargePoint(
        @Body params : ChargePointPostData
    ) : Call<ChargePointResponse>

    @POST("api/users/me/point/charge")
    fun postForceChargePoint(
        @Body params : ForceChargePointPostData
    ) : Call<ChargePointResponse>
}