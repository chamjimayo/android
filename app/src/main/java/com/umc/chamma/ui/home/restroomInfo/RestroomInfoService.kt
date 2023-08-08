package com.umc.chamma.ui.home.restroomInfo

import com.kakao.sdk.common.KakaoSdk.type
import com.umc.chamma.config.App
import com.umc.chamma.models.homemodel.NearToiletResponse
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestroomInfoService(val view : RestroomInfoActivityInterface) {


    fun tryToGetRestroomDetail(restroomId:Int){

        val RestroomInfoRetrofitInterface = App.getRetro().create(RestroomInfoRetrofitInterface::class.java)
        RestroomInfoRetrofitInterface.tryToGetRestroomDetail(restroomId=restroomId)
            .enqueue(object : Callback<RestroomDetailResponse>{
            override fun onResponse(
                call: Call<RestroomDetailResponse>,
                response: Response<RestroomDetailResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onTryToGetRDSuccess(it)
                    else view.onTryToGetRDFailure("API 오류")
                }
            }

            override fun onFailure(call: Call<RestroomDetailResponse>, t: Throwable) {
                view.onTryToGetRDFailure("네트워크 오류")
            }
        })
    }

}