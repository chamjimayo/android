package com.umc.chamma.ui.qr

import com.umc.chamma.config.App
import com.umc.chamma.ui.mypage.chargepoint.ChargePointRetrofitInterface
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoResponse
import com.umc.chamma.ui.qr.model.DeductPointRequest
import com.umc.chamma.ui.qr.model.DeductPointResponse
import com.umc.chamma.ui.qr.model.UseRestroomRequest
import com.umc.chamma.ui.qr.model.UseRestroomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrService(val view : QrActivityInterface) {
    val QrRetrofitInterface = App.getRetro().create(QrRetrofitInterface::class.java)

    fun tryToUseRestroom(restroomId:Int){


        QrRetrofitInterface.tryToUseRestroom(restroomId= UseRestroomRequest(restroomId))
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
        QrRetrofitInterface.tryToDeductPoint(point= DeductPointRequest(point))
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

    fun getUserInfo(){

        val userInfoRetro = App.getRetro().create(ChargePointRetrofitInterface::class.java)
        userInfoRetro.getUserInfo()
            .enqueue(object : Callback<UserinfoResponse>{
                override fun onResponse(
                    call: Call<UserinfoResponse>,
                    response: Response<UserinfoResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) view.onGetUserInfoSuccess(response.body()!!.data)
                        else view.onGetUserInfoFailure("유저정보 불러오기 실패")
                    }
                    if(response.body() == null) view.onGetUserInfoFailure("유저정보 불러오기 실패")
                }

                override fun onFailure(call: Call<UserinfoResponse>, t: Throwable) {
                    view.onGetUserInfoFailure(t.message.toString())
                }
            })
    }
}


