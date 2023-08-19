package com.umc.chamma.ui.home.main

import com.umc.chamma.ui.home.model.NearToiletResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeRetrofitInterface {

    @GET("/api/restroom/nearby/{publicOrPaidOrEntire}")
    fun getNearToilet(
        @Path("publicOrPaidOrEntire") type : String,
        @Query("distance") distance : Double?=null,
        @Query("longitude") longitude : Double,
        @Query("latitude") latitude : Double,
        @Query("sortBy") sortBy : String?=null,
        @Query("page") page : Int?=null,
        @Query("size") size : Int?=null
    ) : Call<NearToiletResponse>
}