package com.umc.chamma.ui.mypage.usage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentReviewBinding
import com.umc.chamma.databinding.FragmentUsageBinding
import com.umc.chamma.ui.home.model.NearToiletResponse
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivity
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.mypage.model.ArticleModel
import com.umc.chamma.ui.mypage.review.ReviewAdapter
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import com.umc.chamma.ui.mypage.review.network.MypageReviewInterface
import com.umc.chamma.ui.mypage.review.network.MypageReviewService
import com.umc.chamma.ui.mypage.usage.model.Content
import com.umc.chamma.ui.mypage.usage.model.MypageUsageData
import com.umc.chamma.ui.mypage.usage.network.MypageUsageInterface
import com.umc.chamma.ui.mypage.usage.network.MypageUsageService
import com.umc.chamma.ui.toiletlist.adapter.ToiletListAdapter


class UsageFragment : BaseFragmentVB<FragmentUsageBinding>(FragmentUsageBinding::bind,R.layout.fragment_usage),
    MypageUsageInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackUsage.setOnClickListener { findNavController().navigateUp() }

        MypageUsageService(this).getUserUsageInfo()
    }


    override fun onGetUserUsageInfoSuccess(data: ArrayList<Content>){

        val adapter = ArticleAdapter(data,::clickItem)
        binding.rvUsage.adapter = adapter
        binding.rvUsage.layoutManager = LinearLayoutManager(context)
    }

    override fun onGetUserUsageInfoFail(message: String) {
        dismissLoading()

        showCustomToast(message)
    }

    private fun clickItem(toiletId : Int){
        val intent = Intent(App.context(), RestroomInfoActivity::class.java)
            .putExtra("ID",toiletId)
        startActivity(intent)
    }
}