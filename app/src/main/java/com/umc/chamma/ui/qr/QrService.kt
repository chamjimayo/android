package com.umc.chamma.ui.qr

import android.util.Log
import com.umc.chamma.config.App
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivityInterface
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoRetrofitInterface
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrService(val view : QrActivityInterface) {
    val QrRetrofitInterface = App.getRetro().create(QrRetrofitInterface::class.java)

    fun tryToUseRestroom(restroomId:Int){


        QrRetrofitInterface.tryToUseRestroom(restroomId=UseRestroomRequest(restroomId))
            .enqueue(object : Callback<UseRestroomResponse>{
            override fun onResponse(
                call: Call<UseRestroomResponse>,
                response: Response<UseRestroomResponse>
            ) {
               // if(response.code()==400)
                    view.onTryToUseRestroomFailure(response.toString())
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

    fun tryToDeductPoint(point:Int){
        QrRetrofitInterface.tryToDeductPoint(point=DeductPointRequest(point))
            .enqueue(object :Callback<DeductPointResponse>{
                override fun onResponse(
                    call: Call<DeductPointResponse>,
                    response: Response<DeductPointResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) view.onTryToDeductPointSuccess(it)
                        else view.onTryToDeductPointFailure("API 오류")
                    }                }

                override fun onFailure(call: Call<DeductPointResponse>, t: Throwable) {
                    view.onTryToDeductPointFailure("네트워크 오류 ${t.toString()}")
                }
            })
    }

}


