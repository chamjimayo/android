package com.umc.chamma.ui.mypage.chargepoint

import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData

interface ChargePointActivityInterface {


    fun onGetUserInfoSuccess(data : UserinfoData)

    fun onGetUserInfoFailure(message : String)

}