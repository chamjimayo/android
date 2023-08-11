package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse


interface ReviewActivityInterface {
    fun onTryToGetRLSuccess(response: ReviewResponse)

    fun onTryToGetRLFailure(message:String)
}