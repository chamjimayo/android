package com.umc.chamma.ui.reviewwrite

import android.os.Bundle
import android.view.View
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentReviewDetailBinding
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivityInterface
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse

class ReviewDetailFragment : BaseFragmentVB<FragmentReviewDetailBinding>(FragmentReviewDetailBinding::bind, R.layout.fragment_review_detail),
    RestroomInfoActivityInterface {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onTryToGetRDSuccess(response: RestroomDetailResponse) {
        TODO("Not yet implemented")
    }

    override fun onTryToGetRDFailure(message: String) {
        showCustomToast(message)
    }
}