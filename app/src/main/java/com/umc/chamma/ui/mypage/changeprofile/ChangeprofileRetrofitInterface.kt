package com.umc.chamma.ui.mypage.changeprofile

import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.changeprofile.model.ChangeprofilePostData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangeprofileRetrofitInterface {

    @POST("/api/users/me/nickname")
    fun changeUserNick(
        @Body params : ChangeprofilePostData
    ): Call<BaseResponse>

    @POST("/api/users/me/profile")
    fun changeUserImg(
        @Body params : ChangeprofilePostData
    ): Call<BaseResponse>
}