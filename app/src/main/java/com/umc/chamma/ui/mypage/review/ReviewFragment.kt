package com.umc.chamma.ui.mypage.review

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentReviewBinding
import com.umc.chamma.databinding.FragmentReviewDetailBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.mypage.model.ListReview
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.model.MypageReviewResponse
import com.umc.chamma.ui.mypage.review.model.PatchreviewPostData
import com.umc.chamma.ui.mypage.review.network.MypageReviewInterface
import com.umc.chamma.ui.mypage.review.network.MypageReviewService
import com.umc.chamma.ui.toiletlist.adapter.ToiletListAdapter


class ReviewFragment : BaseFragmentVB<FragmentReviewBinding>(FragmentReviewBinding::bind,R.layout.fragment_review), MypageReviewInterface {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackReview.setOnClickListener { findNavController().navigateUp() }

        MypageReviewService(this).getUserReviewInfo()
    }

    private fun onClickDelete(reviewId : Int){
        MypageReviewService(this).deleteUserReview(reviewId)
    }

    private fun onClickChange(reviewId : Int){

    }


    override fun onGetUserReviewInfoSuccess(data: ArrayList<MypageReviewData>){

        val adapter = ReviewAdapter(data, ::onClickDelete, ::onClickChange)
        binding.rvReview.adapter = adapter
        binding.rvReview.layoutManager = LinearLayoutManager(context)
    }


    override fun onGetUserReviewInfoFail(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteReviewSuccess() {
        MypageReviewService(this).getUserReviewInfo()
    }

    override fun onDeleteReviewFailure(message: String) {
        showCustomToast(message)
    }

    fun moveReviewPage(){

    }

}