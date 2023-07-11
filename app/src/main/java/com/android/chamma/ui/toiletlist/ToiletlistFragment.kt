package com.android.chamma.ui.toiletlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentToiletListBinding

class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reviewTv.setOnClickListener {
            val intent= Intent(requireContext(), ReviewActivity::class.java)
            startActivity(intent)
        }

    }
}