package com.umc.chamma.ui.qr

import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.ui.qr.model.DeductPointResponse
import com.umc.chamma.ui.qr.model.UseRestroomResponse


interface QrActivityInterface {
    fun onTryToUseRestroomSuccess(response: UseRestroomResponse)

    fun onTryToUseRestroomFailure(message:String)

}