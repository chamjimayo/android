package com.android.chamma.ui.mypage.review

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.R
import com.android.chamma.databinding.FragmentReviewBinding
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.mypage.model.ListReview


class ReviewFragment : Fragment(R.layout.fragment_review) {

    private lateinit var binding: FragmentReviewBinding
    private val reviewAdapter = ReviewAdapter()
    lateinit var mainActivity: MainActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentReview = FragmentReviewBinding.bind(view)
        binding = fragmentReview
        binding.btnBackReview.setOnClickListener { mainActivity.goBackMypage() }

        binding.rvReview.layoutManager = LinearLayoutManager(context)
        binding.rvReview.adapter = reviewAdapter

        reviewAdapter.submitList(mutableListOf<ListReview>().apply {
            add(ListReview("서울숲 화장실",1000000000000,"data:image/jpeg;base64,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","좋아용",5))
            add(ListReview("다산공원 화장실",101010,"data:image/jpeg;base64,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","좋나요?",3))
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }
}