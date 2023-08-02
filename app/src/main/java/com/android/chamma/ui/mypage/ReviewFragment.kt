package com.android.chamma.ui.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.R
import com.android.chamma.databinding.FragmentReviewBinding
import com.android.chamma.ui.main.MainActivity


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
            add(ListReview("서울숲 화장실",1000000000000,"https://images.app.goo.gl/er87SvxaKscis7Di7","좋아용",5))
            add(ListReview("다산공원 화장실",101010,"https://images.app.goo.gl/fs6hTp6KWoy8QU2g7","좋나요?",3))
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }
}