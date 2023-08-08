package com.umc.chamma.ui.toiletlist


import android.os.Bundle
import android.view.View
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentToiletListBinding
import com.umc.chamma.ui.home.main.HomeFragmentInterface
import com.umc.chamma.ui.home.main.HomeService
import com.umc.chamma.ui.home.model.NearToiletResponse


class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list), HomeFragmentInterface{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
//        HomeService(this).getNearToilet("entire",)

   }


    override fun onGetNearToiletSuccess(result: NearToiletResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetNearToiletFailure(message: String) {
        TODO("Not yet implemented")
    }



}