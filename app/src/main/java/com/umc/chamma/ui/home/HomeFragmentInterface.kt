package com.umc.chamma.ui.home

import com.umc.chamma.models.homemodel.NearToiletResponse

interface HomeFragmentInterface {

    fun onGetNearToiletSuccess(result : NearToiletResponse)

    fun onGetNearToiletFailure(message : String)
}