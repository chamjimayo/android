package com.umc.chamma.ui.reviewwrite

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentReviewFirstBinding
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivityInterface
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoService
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse

class ReviewFirstFragment(val restroomId : Int) : BaseFragmentVB<FragmentReviewFirstBinding>(FragmentReviewFirstBinding::bind, R.layout.fragment_review_first),
    RestroomInfoActivityInterface {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RestroomInfoService(this).tryToGetRestroomDetail(restroomId)
    }


    override fun onTryToGetRDSuccess(response: RestroomDetailResponse) {

        Glide.with(this)
            .load(response.data.restroomPhoto[0])
            .error(R.drawable.restroom_ex)
            .into(binding.ivThumnailImg)

        binding.tvRestroomName.text = response.data.restroomName
    }

    override fun onTryToGetRDFailure(message: String) {
        showCustomToast(message)
    }

}