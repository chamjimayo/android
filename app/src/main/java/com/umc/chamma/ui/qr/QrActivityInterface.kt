package com.umc.chamma.ui.qr

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse


interface QrActivityInterface {
    fun onTryToUseRestroomSuccess(response: UseRestroomResponse)

    fun onTryToUseRestroomFailure(message:String)


    fun onTryToDeductPointSuccess(response: DeductPointResponse)

    fun onTryToDeductPointFailure(message:String)

}