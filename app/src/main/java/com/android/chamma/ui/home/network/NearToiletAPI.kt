package com.android.chamma.ui.home.network

import com.android.chamma.models.homemodel.NearToiletResponse
import com.android.chamma.util.Constants
import com.android.chamma.util.Jwt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NearToiletAPI {

    @GET("/api/restroom/nearby/{publicOrPaidOrEntire}")
    fun getNearToilet(
        @Path("publicOrPaidOrEntire") type : String,
        @Query("distance") distance : Double?=null,
        @Query("longitude") longitude : Double,
        @Query("latitude") latitude : Double,
        @Header("x-api-key") api : String? = Constants.xapikey,
        @Header("Bearer-Token") jwt : String? = Jwt.jwt
    ) : Call<NearToiletResponse>
}