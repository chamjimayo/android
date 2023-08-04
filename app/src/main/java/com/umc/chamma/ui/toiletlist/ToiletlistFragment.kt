package com.umc.chamma.ui.toiletlist

import android.content.Intent
<<<<<<< HEAD:app/src/main/java/com/android/chamma/ui/toiletlist/ToiletlistFragment.kt
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

=======
import android.os.Bundle
import android.view.View
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentToiletListBinding
>>>>>>> dev:app/src/main/java/com/umc/chamma/ui/toiletlist/ToiletlistFragment.kt

class ToiletlistFragment : com.umc.chamma.config.BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

   }
}