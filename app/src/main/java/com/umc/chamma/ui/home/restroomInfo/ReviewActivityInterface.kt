package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse


interface ReviewActivityInterface {
    fun onTryToGetRL_HRSuccess(response: ReviewResponse)

    fun onTryToGetRL_HRFailure(message:String)

    fun onTryToGetRL_LatestSuccess(response: ReviewResponse)

    fun onTryToGetRL_LatestFailure(message:String)

    fun onTryToGetRL_LRSuccess(response: ReviewResponse)

    fun onTryToGetRL_LRFailure(message:String)
}