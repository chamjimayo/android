package com.umc.chamma.ui.home.main

import com.umc.chamma.ui.home.model.NearToiletResponse

interface HomeFragmentInterface {

    fun onGetNearToiletSuccess(result : NearToiletResponse)

    fun onGetNearToiletFailure(message : String)
}