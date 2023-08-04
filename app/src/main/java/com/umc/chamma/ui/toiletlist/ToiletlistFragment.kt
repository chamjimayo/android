package com.umc.chamma.ui.toiletlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentToiletListBinding

class ToiletlistFragment : com.umc.chamma.config.BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reviewTv.setOnClickListener {
            val intent= Intent(requireContext(), ReviewActivity::class.java)
            startActivity(intent)
        }

    }
}