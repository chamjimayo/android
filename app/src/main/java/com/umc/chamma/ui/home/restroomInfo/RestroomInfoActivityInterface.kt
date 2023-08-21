package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData


interface RestroomInfoActivityInterface {
    fun onTryToGetRDSuccess(response: RestroomDetailResponse)

    fun onTryToGetRDFailure(message:String)

}