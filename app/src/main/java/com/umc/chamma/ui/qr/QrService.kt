package com.umc.chamma.ui.qr

import com.umc.chamma.config.App
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivityInterface
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoRetrofitInterface
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrService(val view : QrActivityInterface) {


    fun tryToUseRestroom(restroomId:Int){

        val QrRetrofitInterface = App.getRetro().create(QrRetrofitInterface::class.java)
        QrRetrofitInterface.tryToUseRestroom(restroomId=restroomId)
            .enqueue(object : Callback<UseRestroomResponse>{
            override fun onResponse(
                call: Call<UseRestroomResponse>,
                response: Response<UseRestroomResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onTryToUseRestroomSuccess(it)
                    else view.onTryToUseRestroomFailure("API 오류")
                }
            }

                override fun onFailure(call: Call<UseRestroomResponse>, t: Throwable) {
                    view.onTryToUseRestroomFailure("네트워크 오류 ${t.toString()}")
                }
            }
            )
    }

}