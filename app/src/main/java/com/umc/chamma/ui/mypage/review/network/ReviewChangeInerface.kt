package com.umc.chamma.ui.mypage.review.network

interface ReviewChangeInerface {

    fun onPatchReviewSuccess()
    fun onPatchReviewFailure(message : String)
}