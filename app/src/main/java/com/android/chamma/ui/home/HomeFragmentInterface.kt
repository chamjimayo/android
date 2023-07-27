package com.android.chamma.ui.home

import com.android.chamma.models.homemodel.NearToiletResponse

interface HomeFragmentInterface {

    fun onGetNearToiletSuccess(result : NearToiletResponse)

    fun onGetNearToiletFailure(message : String)
}