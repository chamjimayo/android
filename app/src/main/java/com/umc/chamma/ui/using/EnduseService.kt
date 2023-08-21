package com.umc.chamma.ui.using

import com.umc.chamma.config.App
import com.umc.chamma.ui.using.model.EnduseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnduseService(val view : EnduseInterface) {


    fun postEnduse(){
        val enduseRetro = App.getRetro().create(EnduseRetrofitInterface::class.java)
        enduseRetro.postEnduse().enqueue(object : Callback<EnduseResponse>{
            override fun onResponse(
                call: Call<EnduseResponse>,
                response: Response<EnduseResponse>
            ) {
                if(response.body() != null){
                    if(response.code() == 200) view.onPostenduseSuccess(response.body()!!.data)
                    else view.onPostenduseFailure("API 통신 실패")
                }else view.onPostenduseFailure("API 통신 실패")
            }

            override fun onFailure(call: Call<EnduseResponse>, t: Throwable) {
                view.onPostenduseFailure(t.message.toString())
            }
        })
    }
}