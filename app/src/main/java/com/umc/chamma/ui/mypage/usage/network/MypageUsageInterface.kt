package com.umc.chamma.ui.mypage.usage.network

import com.umc.chamma.ui.mypage.usage.model.Content
import com.umc.chamma.ui.mypage.usage.model.MypageUsageData

interface MypageUsageInterface {

    fun onGetUserUsageInfoSuccess(data : ArrayList<Content>)

    fun onGetUserUsageInfoFail(message : String)
}