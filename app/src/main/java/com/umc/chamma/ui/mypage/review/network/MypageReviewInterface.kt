package com.umc.chamma.ui.mypage.review.network

import com.umc.chamma.ui.mypage.review.model.MypageReviewData

interface MypageReviewInterface {

    fun onGetUserReviewInfoSuccess(data: ArrayList<MypageReviewData>)

    fun onGetUserReviewInfoFail(message : String)


}