package com.umc.chamma.ui.mypage.changeprofile

import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.changeprofile.model.ChangeprofilePostData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangeprofileRetrofitInterface {

    @PATCH("api/users/me/Info")
    fun patchProfile(
        @Body params : ChangeprofilePostData
    ) : Call<BaseResponse>
}