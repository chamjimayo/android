package com.android.chamma.ui.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.R
import com.android.chamma.databinding.FragmentUsageBinding
import com.android.chamma.ui.main.MainActivity


class UsageFragment : Fragment(R.layout.fragment_usage) {

    private lateinit var binding: FragmentUsageBinding
    private val articleAdapter = ArticleAdapter()
    lateinit var mainActivity: MainActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentUsageBinding = FragmentUsageBinding.bind(view)
        binding = fragmentUsageBinding

        binding.rvUsage.layoutManager = LinearLayoutManager(context)
        binding.rvUsage.adapter = articleAdapter
        binding.btnBackUsage.setOnClickListener { mainActivity.goBackMypage() }

        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("서울숲 화장실",1000000000000,"https://images.app.goo.gl/er87SvxaKscis7Di7"))
            add(ArticleModel("다산공원 화장실",101010,"https://images.app.goo.gl/fs6hTp6KWoy8QU2g7"))
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }
}