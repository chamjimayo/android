package com.umc.chamma.ui.mypage.changeprofile

import com.umc.chamma.config.App
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.changeprofile.model.ChangeprofilePostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeprofileService(val view : ChangeprofileFragmentInterface) {



    fun changeProfile(data : ChangeprofilePostData){
        val changeUserImgRetro = App.getRetro().create(ChangeprofileRetrofitInterface::class.java)
        changeUserImgRetro.patchProfile(data).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if(response.body() != null){
                    if(response.code() == 200) view.onChangeProfileSuccess("성공")
                    else view.onChangeProfileFailure("API 통신 실패")
                }else view.onChangeProfileFailure("API 통신 실패")
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onChangeProfileFailure(t.message.toString())
            }
        })
    }



}