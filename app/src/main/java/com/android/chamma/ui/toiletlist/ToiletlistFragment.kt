package com.android.chamma.ui.toiletlist

import android.content.Intent
import android.graphics.Paint
import com.android.chamma.R
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentToiletListBinding
import com.android.chamma.ui.home.RestroomVPAdapter
import com.android.chamma.ui.home.ReviewActivity
import me.relex.circleindicator.CircleIndicator3


class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

   }
}