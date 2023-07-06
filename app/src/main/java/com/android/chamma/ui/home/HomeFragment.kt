package com.android.chamma.ui.home

import android.os.Bundle
import android.view.View
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentHomeBinding

class HomeFragment : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}