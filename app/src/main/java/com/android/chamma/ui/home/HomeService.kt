package com.android.chamma.ui.home

import com.android.chamma.config.App
import com.android.chamma.models.homemodel.NearToiletResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view : HomeFragmentInterface) {


    fun getNearToilet(type : String, longitude : Double, latitude : Double,distance : Double?=null){
        val homeRetro = App.getRetrofit().create(HomeRetrofitInterface::class.java)
        homeRetro.getNearToilet(type,longitude = longitude, latitude = latitude, distance = distance)
            .enqueue(object : Callback<NearToiletResponse>{
            override fun onResponse(
                call: Call<NearToiletResponse>,
                response: Response<NearToiletResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onGetNearToiletSuccess(it)
                    else view.onGetNearToiletFailure("API 오류")
                }
            }

            override fun onFailure(call: Call<NearToiletResponse>, t: Throwable) {
                view.onGetNearToiletFailure("네트워크 오류")
            }
        })
    }

}