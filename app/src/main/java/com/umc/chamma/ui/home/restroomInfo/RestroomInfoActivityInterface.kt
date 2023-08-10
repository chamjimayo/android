package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse


interface RestroomInfoActivityInterface {
    fun onTryToGetRDSuccess(response: RestroomDetailResponse)

    fun onTryToGetRDFailure(message:String)
}